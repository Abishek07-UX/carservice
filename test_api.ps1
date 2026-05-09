# This script tests the new REST API for adding service reminders from an "outside system"

$baseUrl = "http://localhost:8080/api/cars"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "   Testing Car Service External API       " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

# 1. Fetch existing cars to get a car ID to attach the task to
Write-Host "1. Fetching existing cars from the system..." -ForegroundColor Yellow
try {
    $cars = Invoke-RestMethod -Uri $baseUrl -Method Get
} catch {
    Write-Host "Error connecting to the server. Make sure it is running!" -ForegroundColor Red
    exit
}

$carId = $null

if ($cars.Count -eq 0) {
    Write-Host "No cars found in the database. Creating a test car..." -ForegroundColor Yellow
    $newCar = @{
        make = "Test"
        model = "Vehicle"
        year = 2024
        registrationNumber = "API-123"
    } | ConvertTo-Json
    
    $createdCar = Invoke-RestMethod -Uri $baseUrl -Method Post -Body $newCar -ContentType "application/json"
    $carId = $createdCar.id
    Write-Host "Created car with ID: $carId" -ForegroundColor Green
} else {
    $carId = $cars[0].id
    Write-Host "Found car with ID: $carId ($($cars[0].make) $($cars[0].model))" -ForegroundColor Green
}

# 2. Add a new service reminder (task) to this car
Write-Host ""
Write-Host "2. Adding a new service reminder from this 'outside script'..." -ForegroundColor Yellow

$dueDate = (Get-Date).AddDays(14).ToString("yyyy-MM-dd")

$newTask = @{
    taskName = "External API Test Reminder"
    dueDate = $dueDate
    completed = $false
} | ConvertTo-Json

$createdTask = Invoke-RestMethod -Uri "$baseUrl/$carId/tasks" -Method Post -Body $newTask -ContentType "application/json"

if ($createdTask) {
    Write-Host "Successfully added a new service reminder!" -ForegroundColor Green
    Write-Host "Task Name: $($createdTask.taskName)"
    Write-Host "Due Date: $($createdTask.dueDate)"
    Write-Host ""
    Write-Host "✅ You can now open http://localhost:8080/car/$carId in your browser to see it." -ForegroundColor Cyan
} else {
    Write-Host "Failed to add service reminder." -ForegroundColor Red
}
