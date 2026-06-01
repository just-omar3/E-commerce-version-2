const API_BASE = '';

class API {
  static getHeaders(auth = true) {
    const headers = { 'Content-Type': 'application/json' };
    if (auth) {
      const token = localStorage.getItem('jwt_token');
      if (token) headers['Authorization'] = 'Bearer ' + token;
    }
    return headers;
  }

  static async handleResponse(res, errorMsg) {
    if (!res.ok) {
      const text = await res.text().catch(() => errorMsg);
      throw new Error(text || errorMsg);
    }
    const contentType = res.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) return res.json();
    return res.text();
  }

  /* Auth */
  static async login(userName, password) {
    const res = await fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: this.getHeaders(false),
      body: JSON.stringify({ userName, password })
    });
    return this.handleResponse(res, 'Invalid username or password');
  }

  static async register(username, email, password, roleName, firstName, lastName, phoneNumber) {
    const payload = { username, email, password, roleName, firstName, lastName, phoneNumber };
    const res = await fetch(`${API_BASE}/auth/register`, {
      method: 'POST',
      headers: this.getHeaders(false),
      body: JSON.stringify(payload)
    });
    return this.handleResponse(res, 'Registration failed');
  }

  /* Products (public) */
  static async getProducts(page = 0, size = 12) {
    const res = await fetch(`${API_BASE}/products?page=${page}&size=${size}`);
    return this.handleResponse(res, 'Failed to load products');
  }

  static async getProduct(id) {
    const res = await fetch(`${API_BASE}/products/${id}`);
    return this.handleResponse(res, 'Product not found');
  }

  static async createProduct(product) {
    const res = await fetch(`${API_BASE}/products`, {
      method: 'POST',
      headers: this.getHeaders(),
      body: JSON.stringify(product)
    });
    return this.handleResponse(res, 'Failed to create product');
  }

  static async updateProduct(id, product) {
    const res = await fetch(`${API_BASE}/products/${id}`, {
      method: 'PUT',
      headers: this.getHeaders(),
      body: JSON.stringify(product)
    });
    return this.handleResponse(res, 'Failed to update product');
  }

  static async deleteProduct(id) {
    const res = await fetch(`${API_BASE}/products/${id}`, {
      method: 'DELETE',
      headers: this.getHeaders()
    });
    if (!res.ok) throw new Error('Failed to delete product');
  }

  static async updateStock(id, stock) {
    const res = await fetch(`${API_BASE}/products/${id}/stock?stock=${stock}`, {
      method: 'PATCH',
      headers: this.getHeaders()
    });
    return this.handleResponse(res, 'Failed to update stock');
  }

  /* Users */
  static async getUsers() {
    const res = await fetch(`${API_BASE}/users`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load users');
  }

  static async getUser(id) {
    const res = await fetch(`${API_BASE}/users/${id}`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load user');
  }

  static async getCurrentUser() {
    const res = await fetch(`${API_BASE}/users/me`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load current user');
  }

  static async createUser(user) {
    const res = await fetch(`${API_BASE}/users`, {
      method: 'POST',
      headers: this.getHeaders(),
      body: JSON.stringify(user)
    });
    return this.handleResponse(res, 'Failed to create user');
  }

  static async updateUser(id, user) {
    const res = await fetch(`${API_BASE}/users/${id}`, {
      method: 'PUT',
      headers: this.getHeaders(),
      body: JSON.stringify(user)
    });
    if (!res.ok) throw new Error('Failed to update user');
  }

  static async deleteUser(id) {
    const res = await fetch(`${API_BASE}/users/${id}`, {
      method: 'DELETE',
      headers: this.getHeaders()
    });
    if (!res.ok) throw new Error('Failed to delete user');
  }

  /* Orders */
  static async getAllOrders() {
    const res = await fetch(`${API_BASE}/orders`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load orders');
  }

  static async getOrdersByUser(userId) {
    const res = await fetch(`${API_BASE}/orders/user/${userId}`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load orders');
  }

  static async getOrderItems(orderId) {
    const res = await fetch(`${API_BASE}/orders/${orderId}/items`, { headers: this.getHeaders() });
    return this.handleResponse(res, 'Failed to load order items');
  }

  static async createOrder(orderRequest) {
    const res = await fetch(`${API_BASE}/orders`, {
      method: 'POST',
      headers: this.getHeaders(),
      body: JSON.stringify(orderRequest)
    });
    return this.handleResponse(res, 'Failed to create order');
  }
}