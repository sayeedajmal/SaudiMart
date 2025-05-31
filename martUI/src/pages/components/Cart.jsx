import React from 'react';

const Cart = () => {
  // Placeholder data for cart items
  const cartItems = [
    {
      id: 1,
      name: 'LCD Monitor',
      price: 650,
      quantity: 1,
      subtotal: 650,
      imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s', // Replace with actual image path
    },
    {
      id: 2,
      name: 'H1 Gamepad',
      price: 550,
      quantity: 2,
      subtotal: 1100,
      imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s', // Replace with actual image path
    },
    // Add more items as needed
  ];

  const cartSummary = {
    subtotal: 1750, // Sum of all item subtotals
    shipping: 'Free',
    total: 1750, // Subtotal + Shipping
  };

  return (
    <div className="container mx-auto py-8">
      {/* Product List Header */}
      <div className="hidden md:grid md:grid-cols-5 gap-4 border-b pb-4 text-gray-600">
        <div className="col-span-2">Product</div>
        <div>Price</div>
        <div>Quantity</div>
        <div>Subtotal</div>
      </div>

      {/* Product List Items */}
      {cartItems.map((item) => (
        <div key={item.id} className="grid grid-cols-1 md:grid-cols-5 gap-4 items-center py-4 border-b">
          <div className="md:col-span-2 flex items-center">
            <img src={item.imageUrl} alt={item.name} className="w-16 h-16 object-contain mr-4" />
            <span className="font-medium">{item.name}</span>
          </div>
          <div className="flex justify-between md:block">
 <span className="md:hidden text-gray-600">Price:</span>
            <span>${item.price}</span>
</div>
          <div>
 <div className="flex justify-between md:block">
 <span className="md:hidden text-gray-600">Quantity:</span>
            <input
              type="number"
              value={item.quantity}
              className="w-16 border rounded-sm text-center"
              min="1"
            />
          </div>
          </div>
          <div>
 <div className="flex justify-between md:block">
 <span className="md:hidden text-gray-600">Subtotal:</span>
            <span>${item.subtotal}</span>
</div>
</div>
        </div>
      ))}

      {/* Action Buttons */}
      <div className="flex justify-between mt-8">
        <button className="px-6 py-3 border rounded-sm">Return To Shop</button>
        <button className="px-6 py-3 border rounded-sm">Update Cart</button>
      </div>

      {/* Coupon and Cart Total */}
      <div className="flex flex-col md:flex-row justify-between mt-8">
        {/* Coupon Code */}
        <div className="flex items-center mb-8 md:mb-0">
          <input
            type="text"
            placeholder="Coupon Code"
            className="border rounded-sm-l px-4 py-3"
          />
          <button className="bg-red-500 text-white px-6 py-3 rounded-sm-r">Apply Coupon</button>
        </div>

        {/* Cart Total Summary */}
        <div className="border rounded-sm p-6 w-full md:w-80">
          <h2 className="text-lg font-bold mb-4">Cart Total</h2>
          <div className="flex justify-between mb-2">
            <span>Subtotal:</span>
            <span>${cartSummary.subtotal}</span>
          </div>
          <div className="flex justify-between mb-4">
            <span>Shipping:</span>
            <span>{cartSummary.shipping}</span>
          </div>
          <div className="flex justify-between font-bold">
            <span>Total:</span>
            <span>${cartSummary.total}</span>
          </div>
          <button className="bg-red-500 text-white px-6 py-3 rounded-sm w-full mt-6">
            Proceed to Checkout
          </button>
        </div>
      </div>
    </div>
  );
};

export default Cart;