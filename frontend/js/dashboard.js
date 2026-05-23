// Dashboard Handler
class DashboardHandler {
    constructor() {
        this.currentTab = 'profile';
        this.initEventListeners();
    }

    initEventListeners() {
        document.getElementById('closeDashboard').addEventListener('click', () => this.closeDashboard());
        document.getElementById('logoutBtn').addEventListener('click', () => {
            authHandler.logout();
        });

        // Dashboard tabs
        const dashboardLinks = document.querySelectorAll('.dashboard-link');
        dashboardLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                if (link.id !== 'logoutBtn') {
                    e.preventDefault();
                    const tab = link.getAttribute('data-tab');
                    this.switchTab(tab);
                }
            });
        });

        document.getElementById('editProfileBtn').addEventListener('click', () => this.editProfile());
        document.getElementById('addAddressBtn').addEventListener('click', () => this.addAddress());
        document.getElementById('changePasswordBtn').addEventListener('click', () => this.changePassword());
    }

    switchTab(tab) {
        // Remove active from all tabs
        document.querySelectorAll('.dashboard-tab').forEach(t => t.classList.remove('active'));
        document.querySelectorAll('.dashboard-link').forEach(l => l.classList.remove('active'));

        // Add active to selected tab
        document.getElementById(tab).classList.add('active');
        document.querySelector(`[data-tab="${tab}"]`).classList.add('active');

        this.currentTab = tab;

        // Load tab data
        if (tab === 'profile') {
            this.loadProfile();
        } else if (tab === 'orders') {
            this.loadOrders();
        } else if (tab === 'addresses') {
            this.loadAddresses();
        } else if (tab === 'wishlist') {
            this.loadWishlist();
        }
    }

    async loadProfile() {
        try {
            const response = await api.get(endpoints.getUserProfile);
            const user = response.data || response;
            
            const profileInfo = document.getElementById('profileInfo');
            profileInfo.innerHTML = `
                <div class="profile-field">
                    <label>Name:</label>
                    <p>${user.name}</p>
                </div>
                <div class="profile-field">
                    <label>Email:</label>
                    <p>${user.email}</p>
                </div>
                <div class="profile-field">
                    <label>Phone:</label>
                    <p>${user.phone}</p>
                </div>
                <div class="profile-field">
                    <label>Member Since:</label>
                    <p>${new Date(user.createdAt).toLocaleDateString()}</p>
                </div>
            `;
        } catch (error) {
            authHandler.showMessage('Failed to load profile', 'error');
        }
    }

    async loadOrders() {
        try {
            const response = await api.get(endpoints.getUserOrders);
            const orders = response.data || response;

            const ordersList = document.getElementById('ordersList');
            
            if (orders.length === 0) {
                ordersList.innerHTML = '<p class="no-orders">No orders yet</p>';
                return;
            }

            ordersList.innerHTML = orders.map(order => this.createOrderElement(order)).join('');
        } catch (error) {
            authHandler.showMessage('Failed to load orders', 'error');
        }
    }

    createOrderElement(order) {
        const total = order.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
        const date = new Date(order.createdAt).toLocaleDateString();

        return `
            <div class="order-item\">\n                <div class=\"order-header\">\n                    <span class=\"order-id\">Order #${order.id}</span>\n                    <span class=\"order-status ${order.status.toLowerCase()}\">${order.status}</span>\n                </div>\n                <div class=\"order-details\">\n                    <p>Date: ${date}</p>\n                    <p>Total: <strong>$${total.toFixed(2)}</strong></p>\n                    <p>Items: ${order.items.length}</p>\n                </div>\n            </div>\n        `;\n    }\n\n    async loadAddresses() {\n        try {\n            const response = await api.get(endpoints.getUserAddresses);\n            const addresses = response.data || response;\n\n            const addressesList = document.getElementById('addressesList');\n            \n            if (addresses.length === 0) {\n                addressesList.innerHTML = '<p class=\"no-addresses\">No saved addresses</p>';\n                return;\n            }\n\n            addressesList.innerHTML = addresses.map(addr => this.createAddressElement(addr)).join('');\n        } catch (error) {\n            authHandler.showMessage('Failed to load addresses', 'error');\n        }\n    }\n\n    createAddressElement(address) {\n        return `\n            <div class=\"address-item\">\n                <div class=\"address-info\">\n                    <p><strong>${address.name}</strong></p>\n                    <p>${address.street}</p>\n                    <p>${address.city}, ${address.state} ${address.zipCode}</p>\n                    <p>${address.country}</p>\n                    <p>Phone: ${address.phone}</p>\n                </div>\n                <button class=\"btn btn-danger\" onclick=\"dashboardHandler.deleteAddress(${address.id})\">Delete</button>\n            </div>\n        `;\n    }\n\n    async loadWishlist() {\n        try {\n            const response = await api.get(endpoints.getWishlist);\n            const wishlistItems = response.data || response;\n\n            const wishlistDiv = document.getElementById('wishlistItems');\n            \n            if (wishlistItems.length === 0) {\n                wishlistDiv.innerHTML = '<p class=\"no-wishlist\">Your wishlist is empty</p>';\n                return;\n            }\n\n            wishlistDiv.innerHTML = wishlistItems.map(item => this.createWishlistItem(item)).join('');\n        } catch (error) {\n            authHandler.showMessage('Failed to load wishlist', 'error');\n        }\n    }\n\n    createWishlistItem(item) {\n        return `\n            <div class=\"wishlist-item\">\n                <div class=\"wishlist-info\">\n                    <p class=\"wishlist-name\">${item.product.name}</p>\n                    <p class=\"wishlist-price\">$${item.product.price.toFixed(2)}</p>\n                </div>\n                <button class=\"btn btn-primary\" onclick=\"cartHandler.addToCart(${item.product.id})\">Add to Cart</button>\n                <button class=\"btn btn-danger\" onclick=\"dashboardHandler.removeWishlistItem(${item.id})\">Remove</button>\n            </div>\n        `;\n    }\n\n    editProfile() {\n        authHandler.showMessage('Edit profile feature coming soon', 'success');\n    }\n\n    addAddress() {\n        authHandler.showMessage('Add address feature coming soon', 'success');\n    }\n\n    async changePassword() {\n        const oldPassword = document.getElementById('oldPassword').value;\n        const newPassword = document.getElementById('newPassword').value;\n        const confirmPassword = document.getElementById('confirmPassword').value;\n\n        if (!oldPassword || !newPassword || !confirmPassword) {\n            authHandler.showMessage('All fields are required', 'error');\n            return;\n        }\n\n        if (newPassword !== confirmPassword) {\n            authHandler.showMessage('Passwords do not match', 'error');\n            return;\n        }\n\n        try {\n            await api.put(endpoints.updateUserProfile, {\n                oldPassword,\n                newPassword\n            });\n            authHandler.showMessage('Password changed successfully', 'success');\n            document.getElementById('oldPassword').value = '';\n            document.getElementById('newPassword').value = '';\n            document.getElementById('confirmPassword').value = '';\n        } catch (error) {\n            authHandler.showMessage(error.message || 'Failed to change password', 'error');\n        }\n    }\n\n    async deleteAddress(addressId) {\n        if (confirm('Are you sure you want to delete this address?')) {\n            try {\n                await api.delete(endpoints.deleteUserAddress(addressId));\n                authHandler.showMessage('Address deleted', 'success');\n                this.loadAddresses();\n            } catch (error) {\n                authHandler.showMessage('Failed to delete address', 'error');\n            }\n        }\n    }\n\n    async removeWishlistItem(itemId) {\n        try {\n            await api.delete(endpoints.removeFromWishlist(itemId));\n            authHandler.showMessage('Removed from wishlist', 'success');\n            this.loadWishlist();\n        } catch (error) {\n            authHandler.showMessage('Failed to remove from wishlist', 'error');\n        }\n    }\n\n    closeDashboard() {\n        document.getElementById('dashboardModal').style.display = 'none';\n    }\n}\n\n// Initialize Dashboard\nconst dashboardHandler = new DashboardHandler();\n\n// Function to load dashboard\nasync function loadDashboard() {\n    dashboardHandler.loadProfile();\n}