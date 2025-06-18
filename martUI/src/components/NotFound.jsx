import React from 'react';
import { Link } from 'react-router-dom'; 

const NotFound = () => {
  return (
    <div className="flex h-[70vh] flex-col items-center justify-center min-h-screen-minus-header-footer py-8 px-4"> {/* Assuming min-h-screen-minus-header-footer is defined in your CSS to account for header/footer height */}
      {/* Breadcrumbs */}
      <div className="text-sm text-gray-600 mb-8">
        <Link to="/" className="hover:underline">Home</Link> / <span className="text-gray-800 font-medium">404 Error</span>
      </div>

      {/* 404 Content */}
      <div className="flex flex-col items-center text-center">
        <h1 className="text-6xl md:text-9xl font-bold text-gray-800 mb-4">404 Not Found</h1>
        <p className="text-lg text-gray-600 mb-8">Your visited page not found. You may go home page.</p>
        <Link to="/">
          <button className="bg-red-500 text-white px-6 py-3 rounded-md hover:bg-red-600 transition duration-300">
            Back to home page
          </button>
        </Link>
      </div>
    </div>
  );
};

export default NotFound;