// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Initialize the shop
    initShop();
});

// Initialize the shop functionality
function initShop() {
    // Load products on the home page
    if (document.getElementById('product-grid')) {
        loadProducts();
    }
    
    // Update cart count
    updateCartCount();
    
    // Initialize cart page if we're on it
    if (document.querySelector('.cart-page')) {
        loadCartItems();
    }
    
    // Initialize checkout page if we're on it
    if (document.querySelector('.checkout-page')) {
        loadCheckoutSummary();
    }
}

// Load products into the product grid
function loadProducts() {
    const productGrid = document.getElementById('product-grid');
    
    products.forEach(product => {
        const productCard = createProductCard(product);
        productGrid.appendChild(productCard);
    });
}

// Create a product card element
function createProductCard(product) {
    const card = document.createElement('div');
    card.className = 'product-card';
    
    card.innerHTML = `
        <div class="product-image">
            <img src="${product.image}" alt="${product.name}">
        </div>
        <div class="product-info">
            <h3 class="product-title">${product.name}</h3>
            <p class="product-price">$${product.price.toFixed(2)}</p>
            <div class="product-actions">
                <button class="add-to-cart" data-id="${product.id}">Add to Cart</button>
                <a href="product.html?id=${product.id}" class="view-details">Details <i class="fas fa-chevron-right"></i></a>
            </div>
        </div>
    `;
    
    // Add event listener to the Add to Cart button
    card.querySelector('.add-to-cart').addEventListener('click', function() {
        addToCart(product);
    });
    
    return card;
}

// Cart functionality
function getCart() {
    const cart = localStorage.getItem('cart');
    return cart ? JSON.parse(cart) : [];
}

function saveCart(cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
}

function addToCart(product) {
    const cart = getCart();
    
    // Check if product is already in cart
    const existingItem = cart.find(item => item.id === product.id);
    
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({
            id: product.id,
            name: product.name,
            price: product.price,
            image: product.image,
            quantity: 1
        });
    }
    
    saveCart(cart);
    updateCartCount();
    
    // Show a brief notification
    showNotification(`${product.name} added to cart!`);
}

function updateCartCount() {
    const cartCount = document.getElementById('cart-count');
    if (!cartCount) return;
    
    const cart = getCart();
    const count = cart.reduce((total, item) => total + item.quantity, 0);
    
    cartCount.textContent = count;
}

function showNotification(message) {
    // Check if a notification container already exists
    let notification = document.querySelector('.notification');
    
    if (!notification) {
        // Create a new notification container
        notification = document.createElement('div');
        notification.className = 'notification';
        document.body.appendChild(notification);
    }
    
    // Set the message
    notification.textContent = message;
    notification.classList.add('show');
    
    // Hide the notification after 3 seconds
    setTimeout(() => {
        notification.classList.remove('show');
    }, 3000);
}

// Cart page functionality
function loadCartItems() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartSummaryContainer = document.getElementById('cart-summary');
    const cart = getCart();
    
    if (cart.length === 0) {
        // Show empty cart message
        document.querySelector('.cart-container').innerHTML = `
            <div class="empty-cart">
                <i class="fas fa-shopping-cart"></i>
                <h2>Your cart is empty</h2>
                <p>Looks like you haven't added any items to your cart yet.</p>
                <a href="index.html" class="btn">Continue Shopping</a>
            </div>
        `;
        return;
    }
    
    // Clear existing items
    cartItemsContainer.innerHTML = '';
    
    // Add cart header
    const cartHeader = document.createElement('div');
    cartHeader.className = 'cart-header';
    cartHeader.innerHTML = `
        <div>Product</div>
        <div>Price</div>
        <div>Quantity</div>
        <div>Total</div>
    `;
    cartItemsContainer.appendChild(cartHeader);
    
    // Add cart items
    let subtotal = 0;
    
    cart.forEach(item => {
        const product = products.find(p => p.id === item.id);
        if (!product) return;
        
        const itemTotal = product.price * item.quantity;
        subtotal += itemTotal;
        
        const cartItem = document.createElement('div');
        cartItem.className = 'cart-item';
        cartItem.innerHTML = `
            <div class="cart-product">
                <img src="${product.image}" alt="${product.name}">
                <div class="cart-product-info">
                    <h3>${product.name}</h3>
                    <p>${product.category}</p>
                </div>
            </div>
            <div class="cart-price">$${product.price.toFixed(2)}</div>
            <div class="cart-quantity">
                <button class="quantity-btn minus" data-id="${product.id}">-</button>
                <input type="number" class="quantity-input" value="${item.quantity}" min="1" data-id="${product.id}">
                <button class="quantity-btn plus" data-id="${product.id}">+</button>
            </div>
            <div class="cart-item-total">
                $${itemTotal.toFixed(2)}
                <span class="cart-remove" data-id="${product.id}"><i class="fas fa-trash"></i></span>
            </div>
        `;
        
        cartItemsContainer.appendChild(cartItem);
        
        // Add event listeners for quantity buttons
        cartItem.querySelector('.minus').addEventListener('click', function() {
            updateCartItemQuantity(product.id, -1);
        });
        
        cartItem.querySelector('.plus').addEventListener('click', function() {
            updateCartItemQuantity(product.id, 1);
        });
        
        cartItem.querySelector('.quantity-input').addEventListener('change', function(e) {
            updateCartItemQuantity(product.id, 0, parseInt(e.target.value));
        });
        
        cartItem.querySelector('.cart-remove').addEventListener('click', function() {
            removeCartItem(product.id);
        });
    });
    
    // Calculate summary values
    const shipping = subtotal > 100 ? 0 : 10;
    const tax = subtotal * 0.1; // 10% tax
    const total = subtotal + shipping + tax;
    
    // Update cart summary
    cartSummaryContainer.innerHTML = `
        <h3>Order Summary</h3>
        <div class="summary-item">
            <span>Subtotal</span>
            <span>$${subtotal.toFixed(2)}</span>
        </div>
        <div class="summary-item">
            <span>Shipping</span>
            <span>${shipping === 0 ? 'Free' : '$' + shipping.toFixed(2)}</span>
        </div>
        <div class="summary-item">
            <span>Tax (10%)</span>
            <span>$${tax.toFixed(2)}</span>
        </div>
        <div class="summary-total">
            <span>Total</span>
            <span>$${total.toFixed(2)}</span>
        </div>
        <button class="checkout-btn" onclick="window.location.href='checkout.html'">Proceed to Checkout</button>
    `;
}

function updateCartItemQuantity(productId, change, newQuantity) {
    const cart = getCart();
    const itemIndex = cart.findIndex(item => item.id === productId);
    
    if (itemIndex === -1) return;
    
    if (newQuantity !== undefined) {
        // Set to specific quantity
        cart[itemIndex].quantity = Math.max(1, newQuantity);
    } else {
        // Increment/decrement quantity
        cart[itemIndex].quantity = Math.max(1, cart[itemIndex].quantity + change);
    }
    
    saveCart(cart);
    loadCartItems();
    updateCartCount();
}

function removeCartItem(productId) {
    let cart = getCart();
    cart = cart.filter(item => item.id !== productId);
    saveCart(cart);
    loadCartItems();
    updateCartCount();
}

// Checkout page functionality
function loadCheckoutSummary() {
    const orderSummaryContainer = document.getElementById('order-summary');
    const cart = getCart();
    
    if (cart.length === 0) {
        window.location.href = 'cart.html';
        return;
    }
    
    let subtotal = 0;
    let itemsHtml = '';
    
    cart.forEach(item => {
        const product = products.find(p => p.id === item.id);
        if (!product) return;
        
        const itemTotal = product.price * item.quantity;
        subtotal += itemTotal;
        
        itemsHtml += `
            <div class="order-item">
                <div class="order-item-name">
                    <span class="order-item-quantity">${item.quantity}</span>
                    ${product.name}
                </div>
                <div class="order-item-price">$${itemTotal.toFixed(2)}</div>
            </div>
        `;
    });
    
    // Calculate summary values
    const shipping = subtotal > 100 ? 0 : 10;
    const tax = subtotal * 0.1; // 10% tax
    const total = subtotal + shipping + tax;
    
    // Update order summary
    orderSummaryContainer.innerHTML = `
        <h3>Order Summary</h3>
        ${itemsHtml}
        <div class="summary-item">
            <span>Subtotal</span>
            <span>$${subtotal.toFixed(2)}</span>
        </div>
        <div class="summary-item">
            <span>Shipping</span>
            <span>${shipping === 0 ? 'Free' : '$' + shipping.toFixed(2)}</span>
        </div>
        <div class="summary-item">
            <span>Tax (10%)</span>
            <span>$${tax.toFixed(2)}</span>
        </div>
        <div class="summary-total">
            <span>Total</span>
            <span>$${total.toFixed(2)}</span>
        </div>
    `;
    
    // Add event listener to the checkout form
    document.getElementById('checkout-form').addEventListener('submit', function(e) {
        e.preventDefault();
        processCheckout();
    });
}

function processCheckout() {
    // Here we would normally send the order to the backend
    // For now, we'll just show a success message and clear the cart
    
    // In a real implementation, this is where we would integrate with Klarna API
    // For example:
    // 1. Create an order in the backend
    // 2. Initialize Klarna Checkout or Klarna Payments
    // 3. Redirect to Klarna or show Klarna widget
    
    // For this dummy implementation, we'll just show a success message
    document.querySelector('.checkout-container').innerHTML = `
        <div class="checkout-success">
            <i class="fas fa-check-circle"></i>
            <h2>Order Placed Successfully!</h2>
            <p>Thank you for your purchase. Your order has been placed successfully.</p>
            <p>Order #: ORD-${Math.floor(Math.random() * 10000)}</p>
            <p>A confirmation email has been sent to your email address.</p>
            <a href="index.html" class="btn">Continue Shopping</a>
        </div>
    `;
    
    // Clear the cart
    localStorage.removeItem('cart');
    updateCartCount();
}

// Add some CSS for notifications
const style = document.createElement('style');
style.textContent = `
    .notification {
        position: fixed;
        top: 20px;
        right: 20px;
        background-color: var(--primary-color);
        color: white;
        padding: 10px 20px;
        border-radius: 4px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        transform: translateX(150%);
        transition: transform 0.3s ease-in-out;
        z-index: 1000;
    }
    
    .notification.show {
        transform: translateX(0);
    }
    
    .checkout-success {
        text-align: center;
        padding: 40px;
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    }
    
    .checkout-success i {
        font-size: 60px;
        color: var(--success-color);
        margin-bottom: 20px;
    }
    
    .checkout-success h2 {
        margin-bottom: 15px;
    }
    
    .checkout-success p {
        margin-bottom: 10px;
        color: var(--light-text);
    }
    
    .checkout-success .btn {
        margin-top: 20px;
    }
`;
document.head.appendChild(style);
