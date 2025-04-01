# StyleShop Frontend

This is a dummy online shop frontend for the Klarna payment API integration project. The frontend is built with HTML, CSS, and JavaScript and provides a complete shopping experience with product listings, product details, shopping cart, and checkout functionality.

## Structure

- `index.html` - Main page with product listings
- `product.html` - Product details page
- `cart.html` - Shopping cart page
- `checkout.html` - Checkout page with placeholder for Klarna payment integration
- `css/styles.css` - Stylesheet for the entire application
- `js/products.js` - Product data
- `js/main.js` - Core functionality for the shop

## Features

- Responsive design that works on desktop and mobile devices
- Product listing with grid layout
- Product details page with quantity selector
- Shopping cart with quantity adjustment
- Order summary with subtotal, shipping, and tax calculations
- Checkout form with shipping information
- Placeholder for Klarna payment integration

## Klarna Integration

The frontend is designed to be integrated with the Klarna payment API. The checkout page includes a placeholder for the Klarna payment widget.

### Future Integration Steps

1. **Backend Integration**
   - Use the existing Java backend to create a session with Klarna API
   - Generate a client token for the frontend

2. **Frontend Integration**
   - Include the Klarna JavaScript SDK
   - Initialize Klarna Payments with the client token from the backend
   - Load the Klarna payment widget in the designated container
   - Handle authorization and completion of the payment

3. **Checkout Flow**
   - When a user clicks "Complete Order", the frontend will:
     - Collect shipping information
     - Send order details to the backend
     - The backend will create an order with Klarna
     - The frontend will authorize the payment with Klarna
     - Upon successful authorization, the order will be completed

## How to Use

1. Open `index.html` in a web browser to view the shop
2. Browse products and add them to your cart
3. View your cart and proceed to checkout
4. Fill in the shipping information
5. Select Klarna as the payment method (currently a placeholder)
6. Complete the order (currently shows a success message without actual payment processing)

## Notes for Developers

- The shop uses localStorage to store cart data
- Product data is stored in `js/products.js` and can be modified to add/remove products
- The checkout page includes commented code showing how Klarna integration would be implemented
- The backend Java code already includes models and services for Klarna API integration
