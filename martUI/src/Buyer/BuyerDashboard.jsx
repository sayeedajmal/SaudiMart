import React from 'react';
import { MdSearch, MdFavoriteBorder, MdOutlineRemoveRedEye, MdOutlineAddShoppingCart, MdStar } from 'react-icons/md';
import Categories from "./Categories"
import FeaturedProduct from "./FeaturedProduct"
import ProductCard from './ProductCard';
function BuyerDashboard() {

  const products = [
    {
      id: 1,
      image: 'https://via.placeholder.com/270x192', // Replace with actual image URL
      discount: '40%',
      title: 'HAVIT HV-G92 Gamepad',
      price: '$120',
      originalPrice: '$160',
      rating: 4, // Assuming a rating out of 50128
      reviews: 88,
    },
    {
      id: 2,
      image: 'https://via.placeholder.com/270x192', // Replace with actual image URL
      discount: '35%',
      title: 'Kruger&Matz KM135',
      price: '$960',
      originalPrice: '$1100',
      rating: 5,
      reviews: 120,
    },
    {
      id: 3,
      image: 'https://via.placeholder.com/270x192', // Replace with actual image URL
      discount: '30%',
      title: 'IPS LCD Gaming Monitor',
      price: '$370',
      originalPrice: '$400',
      rating: 4,
      reviews: 95,
    },
    {
      id: 4,
      image: 'https://via.placeholder.com/270x192', // Replace with actual image URL
      discount: '25%',
      title: 'CANON EOS DSLR Camera',
      price: '$375',
      originalPrice: '$500',
      rating: 5,
      reviews: 150,
    },
    // Add more product objects as needed
  ];

  return (
    <div className="min-h-screen text-black" style={{ backgroundColor: 'var(--primary-color-1)' }}>
      {/* Promotional Banner */}
      <div className="text-white text-center py-2 text-sm" style={{ backgroundColor: 'var(--neutral-900)' }}>
        Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%! <a href="#" className="underline">ShopNow</a>
      </div>

      {/* Header */}
      <header className="container mx-auto px-4 py-2 flex justify-between items-center">
        <h1 className="text-2xl font-bold" style={{ color: 'var(--neutral-900)' }}>Exclusive</h1>
        <nav className="hidden md:flex space-x-6">
          <a href="#" className="hover:underline" style={{ color: 'var(--neutral-800)' }}>Home</a>
          <a href="#" className="hover:underline" style={{ color: 'var(---neutral-800)' }}>Contact</a>
          <a href="#" className="hover:underline" style={{ color: 'var(--neutral-800)' }}>About</a>
          <a href="#" className="hover:underline" style={{ color: 'var(--neutral-800)' }}>Sign Up</a>
        </nav>
        <div className="flex items-center space-x-4">
          <div className="relative">
            <input
              type="text"
              placeholder="What are you looking for?"
              className="pl-8 pr-3 py-2  text-sm"
              style={{ backgroundColor: 'var(grayscale-label)', color: 'var(--neutral-800)', border: '1px solid var(--neutral-700)' }}
            />
            <MdSearch className="absolute left-2 top-2.5" size={18} style={{ color: 'var(--neutral-400)' }} />
          </div>
          <div className="flex items-center space-x-2">
            <div className="w-8 h-8  bg-white flex items-center justify-center cursor-pointer">
              <MdFavoriteBorder size={20} style={{ color: 'var(--neutral-700)' }} />
            </div>
            <div className="w-8 h-8  bg-white flex items-center justify-center cursor-pointer">
              <MdOutlineAddShoppingCart size={20} style={{ color: 'var(--neutral-700)' }} />
            </div>
          </div>
        </div>
      </header>

      {/* Main Banner */}
      <section className="relative">
        <div className="container mx-auto px-6 py-4 flex flex-col md:flex-row items-start justify-between">
          {/* Left Sidebar - Categories */}
          <ul className="space-y-4 text-sm font-bold md:w-1/4" style={{ color: 'var(--neutral-800)' }}>
            <li><a href="#" className="hover:underline">Woman's Fashion</a></li>
            <li><a href="#" className="hover:underline">Men's Fashion</a></li>
            <li><a href="#" className="hover:underline">Electronics</a></li>
            <li><a href="#" className="hover:underline">Home & Lifestyle</a></li>
            <li><a href="#" className="hover:underline">Medicine</a></li>
            <li><a href="#" className="hover:underline">Sports & Outdoor</a></li>
            <li><a href="#" className="hover:underline">Baby's & Toys</a></li>
            <li><a href="#" className="hover:underline">Groceries & Pets</a></li>
            <li><a href="#" className="hover:underline">Health & Beauty</a></li>
          </ul>

          {/* Right Banner */}
          <div className="md:w-3/4 flex flex-col md:flex-row items-center justify-between text-white md:pl-12">
            <div className="md:w-1/2 mb-8 md:mb-0">
              <img src="/path/to/apple-logo.png" alt="Apple Logo" className="mb-4" style={{ maxWidth: '50px' }} />
              <h2 className="text-4xl font-bold mb-4">iPhone 14 Series</h2>
              <p className="text-xl mb-6">Up to 10% off Voucher</p>
              <a href="#" className="flex items-center font-medium" style={{ color: 'var(--grayscale-off-white)' }}>
                Shop Now
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 ml-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 5l7 7m0 0l-7 7m7-7H3" />
                </svg>
              </a>
            </div>
            <div className="md:w-1/2 flex justify-center">
              <img src="https://via.placeholder.com/400x400" alt="iPhone 14" className="max-w-full h-auto" />
            </div>
          </div>
        </div>

        {/* Slider indicators */}
        <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
          <span className="block w-3 h-3  bg-gray-600"></span>
          <span className="block w-3 h-3  bg-gray-600"></span>
          <span className="block w-3 h-3  bg-gray-600"></span>
          <span className="block w-3 h-3  bg-white"></span> {/* Active */}
        </div>
      </section>


      {/* Today's Section */}
      <section className="container mx-auto px-6">
        <div className="flex items-center mb-8">
          <div className="w-5 h-10 mr-4" style={{ backgroundColor: 'var(--color-secondary)' }}></div>
          <h3 className="text-2xl font-semibold" style={{ color: 'var(--button-color-2)' }}>Today's</h3>
        </div>

        <div className="relative">
          <div className="flex overflow-x-auto space-x-6 pb-4 no-scrollbar">
            {/* Product Card 1 */}
            {products.map(product => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>

          {/* Navigation Arrows */}
          <button className="absolute top-1/2 -translate-y-1/2 left-0 bg-white  w-8 h-8 flex items-center justify-center shadow-md">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-700" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
          </button>
          <button className="absolute top-1/2 -translate-y-1/2 right-0 bg-white  w-8 h-8 flex items-center justify-center shadow-md">
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-gray-700" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </button>
        </div>

        <div className="text-center mt-8">
          <button className="px-6 py-4  text-white font-medium" style={{ backgroundColor: 'var(--button-color-2)' }}>
            View All Products
          </button>
        </div>

        <Categories />
        <FeaturedProduct />

      </section>

    </div>
  );
}

export default BuyerDashboard;