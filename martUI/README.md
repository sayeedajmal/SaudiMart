# saudiMART Frontend (martUI)

This directory contains the frontend application for the saudiMART B2B e-commerce platform. Built with modern web technologies, it provides a user-friendly interface for both buyers and sellers.

## Technologies Used

*   **React**: A JavaScript library for building user interfaces.
*   **Redux**: A predictable state container for managing application state.
*   **Tailwind CSS**: A utility-first CSS framework for rapidly styling the UI.
*   **Vite**: A fast build tool for modern web development.

## Project Structure

The project follows a structured approach to organize code for maintainability and scalability.



# CURRENT STRUCTURE
martUI/src/
├── api/
│   └── auth.js
├── App.jsx
├── assets/
│   └── react.svg
├── components/
│   ├── Cart.jsx
│   ├── Categories.jsx
│   ├── Checkout.jsx
│   ├── Contact.jsx
│   ├── EditProfile.jsx
│   ├── FeaturedProduct.jsx
│   ├── Footer.jsx
│   ├── FullScreenLoader.css
│   ├── FullScreenLoader.jsx
│   ├── Header.jsx
│   ├── LoginPage.jsx
│   ├── NotFound.jsx
│   ├── NotificationDisplay.jsx
│   ├── SignupPage.jsx
│   ├── WishProduct.jsx
│   └── Wishlist.jsx
├── index.css
├── main.jsx
├── pages/
│   ├── buyer/
│   │   └── BuyerDashboard.jsx
│   └── seller/
│       └── SellerDashboard.jsx
└── store/
    ├── authActions.js
    ├── authReducer.js
    ├── notificationActions.js
    ├── notificationReducer.js
    └── store.js
