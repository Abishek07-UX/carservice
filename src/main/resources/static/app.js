/**
 * Shared logic for the Car Service SaaS
 */

const API_BASE = 'http://localhost:8080/api';

function getLoggedUser() {
    const user = localStorage.getItem('loggedUser');
    return user ? JSON.parse(user) : null;
}

function setLoggedUser(user) {
    localStorage.setItem('loggedUser', JSON.stringify(user));
}

function logout() {
    localStorage.removeItem('loggedUser');
    window.location.href = 'index.html';
}

function requireAuth() {
    if (!getLoggedUser()) {
        window.location.href = 'index.html';
    }
}

function requireAdmin() {
    const user = getLoggedUser();
    if (!user || user.role !== 'ADMIN') {
        window.location.href = 'index.html';
    }
}

// Utility to handle API errors
async function handleResponse(response) {
    if (!response.ok) {
        const error = await response.text();
        throw new Error(error || response.statusText);
    }
    return response.json();
}
