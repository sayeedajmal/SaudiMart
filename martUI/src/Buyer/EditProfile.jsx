import React from 'react';

const EditProfile = () => {
  return (
    <div className="container m-4  md:px-0">
      <div className="mb-4 text-sm text-gray-600">
       Edit Profile
      </div>
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-2xl font-semibold">My Account</h1>
        <span className="text-red-500">Welcome! Md Rimel</span>
      </div>
      <div className="flex flex-col md:flex-row gap-16">
        {/* Sidebar Navigation */}
        <div className="md:w-1/4 flex flex-col space-y-4">
          <h3 className="text-base font-bold">Manage My Account</h3>
          <ul className="space-y-4 text-gray-600">
            <li><a href="#" className="text-red-500 font-semibold">My Profile</a></li>
            <li><a href="#" className="hover:text-red-500">Address Book</a></li>
            <li><a href="#" className="hover:text-red-500">My Payment Options</a></li>
          </ul>
          <h3 className="text-lg font-bold mt-8 mb-4">My Orders</h3>
          <ul className="space-y-2">
            <li><a href="#">My Returns</a></li>
            <li><a href="#">My Cancellations</a></li>
          </ul>
          <h3 className="text-lg font-bold mt-8 mb-4"><a href="#">My Wishlist</a></h3>
        </div>

        {/* Main Content Area - Edit Profile Form */}
        <div className="md:w-3/4 bg-white p-8 rounded-md shadow">
          <h2 className="text-xl font-semibold text-red-500 mb-6">Edit Your Profile</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
            <div>
              <label htmlFor="firstName" className="block text-gray-700 text-sm mb-2">First Name</label>
              <input
                type="text"
                id="firstName"
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
            <div>
              <label htmlFor="lastName" className="block text-gray-700 text-sm mb-2">Last Name</label>
              <input
                type="text"
                id="lastName"
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
            <div>
              <label htmlFor="email" className="block text-gray-700 text-sm mb-2">Email</label>
              <input
                type="email"
                id="email"
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
            <div>
              <label htmlFor="address" className="block text-gray-700 text-sm mb-2">Address</label>
              <input
                type="text"
                id="address"
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
          </div>

          <h3 className="text-xl font-semibold text-red-500 mb-6">Password Changes</h3>
          <div className="grid grid-cols-1 gap-4 mb-6">
            <div>
              <label htmlFor="currentPassword" className="block text-gray-700 text-sm mb-2">Current Password</label>
              <input
                type="password"
                id="currentPassword"
                placeholder="Current Passwod" // Placeholder text from image
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
            <div>
              <label htmlFor="newPassword" className="block text-gray-700 text-sm mb-2">New Password</label>
              <input
                type="password"
                id="newPassword"
                placeholder="New Passwod" // Placeholder text from image
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
            <div>
              <label htmlFor="confirmNewPassword" className="block text-sm font-medium text-gray-700">Confirm New Password</label>
              <input
                type="password"
                id="confirmNewPassword"
                placeholder="Confirm New Passwod" // Placeholder text from image
                className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-red-500 focus:border-red-500"
              />
            </div>
          </div>

          <button className="px-6 py-3 border border-gray-300 rounded-md text-gray-700">Cancel</button>
          <button className="px-6 py-3 bg-red-500 text-white rounded-md hover:bg-red-600 transition duration-300">Save Changes</button>
        </div>
      </div>
    </div>
  );
};

export default EditProfile;