import React from 'react';

const Checkout = () => {
  // Placeholder data for items in order summary
  const checkoutItems = [
    {
      id: 1,
      name: 'LCD Monitor',
      price: 650,
      imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s', // Replace with actual image path
    },
    {
      id: 2,
      name: 'H1 Gamepad',
      price: 1100,
      imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s', // Replace with actual image path
    },
    // Add more items as needed
  ];

  const orderSummary = {
    subtotal: 1750, // Sum of all item subtotals
    shipping: 'Free',
    total: 1750, // Subtotal + Shipping
  };

  return (
    <div className="container py-8 px-2 md:px-0 m-2">
      {/* Header */}
      <div className="text-sm text-gray-600 mb-8">
       <span className="text-black text-lg">Checkout</span>
      </div>

      <h2 className="text-2xl font-bold mb-8">Billing Details</h2>

      <div className="flex flex-col lg:flex-row gap-8">
        {/* Billing Details Form */}
        <div className="flex-1">
          <form className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="col-span-1">
              <label htmlFor="firstName" className="block text-gray-600 text-sm mb-2">First Name*</label>
              <input type="text" id="firstName" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-1">
              <label htmlFor="companyName" className="block text-gray-600 text-sm mb-2">Company Name</label>
              <input type="text" id="companyName" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-2">
              <label htmlFor="streetAddress" className="block text-gray-600 text-sm mb-2">Street Address*</label>
              <input type="text" id="streetAddress" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-2">
              <label htmlFor="apartment" className="block text-gray-600 text-sm mb-2">Apartment, floor, etc. (optional)</label>
              <input type="text" id="apartment" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-1">
              <label htmlFor="townCity" className="block text-gray-600 text-sm mb-2">Town/City*</label>
              <input type="text" id="townCity" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-1">
              <label htmlFor="phoneNumber" className="block text-gray-600 text-sm mb-2">Phone Number*</label>
              <input type="text" id="phoneNumber" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-2">
              <label htmlFor="emailAddress" className="block text-gray-600 text-sm mb-2">Email Address*</label>
              <input type="email" id="emailAddress" className="w-full border rounded-sm px-3 py-2" />
            </div>
            <div className="col-span-2 flex items-center">
              <input type="checkbox" id="saveInfo" className="mr-2" />
              <label htmlFor="saveInfo" className="text-sm text-gray-600">Save this information for faster check-out next time</label>
            </div>
          </form>
        </div>

        {/* Order Summary and Payment */}
        <div className="flex-1">
          {/* Order Summary */}
          <div className="border rounded-sm p-6 mb-8">
            <h3 className="text-lg font-bold mb-4">Product</h3>
            {checkoutItems.map((item) => (
              <div key={item.id} className="flex justify-between items-center mb-4">
                <div className="flex items-center">
                  <img src={item.imageUrl} alt={item.name} className="w-12 h-12 object-contain mr-4" />
                  <span>{item.name}</span>
                </div>
                <span>${item.price.toFixed(2)}</span>
              </div>
            ))}
            <div className="border-t pt-4 mt-4">
              <div className="flex justify-between mb-2">
                <span>Subtotal:</span>
                <span>${orderSummary.subtotal.toFixed(2)}</span>
              </div>
              <div className="flex justify-between mb-4">
                <span>Shipping:</span>
                <span>{orderSummary.shipping}</span>
              </div>
              <div className="flex justify-between font-bold text-black">
                <span>Total:</span>
                <span>${orderSummary.total.toFixed(2)}</span>
              </div>
            </div>
          </div>

          {/* Payment Method */}
          <div className="mb-6">
            <div className="flex items-center mb-4">
              <input type="radio" id="bank" name="paymentMethod" value="bank" className="mr-2" />
              <label htmlFor="bank" className="mr-4">Bank</label>
              <div className="flex items-center gap-2">
                {/* Placeholder for payment icons */}
                <img src="https://via.placeholder.com/30x20?text=Visa" alt="Visa" />
                <img src="https://via.placeholder.com/30x20?text=Mastercard" alt="Mastercard" />
                <img src="https://via.placeholder.com/30x20?text=Amex" alt="Amex" />
              </div>
            </div>
            <div className="flex items-center">
              <input type="radio" id="cash" name="paymentMethod" value="cash" className="mr-2" />
              <label htmlFor="cash">Cash on delivery</label>
            </div>
          </div>

          {/* Coupon Code */}
          <div className="flex flex-col md:flex-row gap-4 mb-8">
            <input type="text" placeholder="Coupon Code" className="flex-1 border rounded-sm px-3 py-2" />
            <button className="bg-red-500 text-white px-6 py-3 rounded-sm">Apply Coupon</button>
          </div>

          {/* Place Order Button */}
          <button className="bg-red-500 text-white px-6 py-3 rounded-sm w-full">Place Order</button>
        </div>
      </div>
    </div>
  );
};

export default Checkout;