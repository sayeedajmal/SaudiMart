import React from 'react';

/**
 * Displays a featured products section with highlighted items and store features.
 *
 * Renders a visually prominent section showcasing a main featured product, a grid of additional featured products, and a row of store feature highlights such as free delivery, customer service, and money-back guarantee. Designed for responsive layouts with styled backgrounds and images.
 *
 * @returns {JSX.Element} The featured products section for the storefront.
 */
function FeaturedProduct() {
  return (
    <section className="py-12 px-4" style={{ backgroundColor: 'var(--primary-color-1)' }}>
      <div className="container mx-auto">
        <div className="flex items-center mb-8">
          <div className="w-4 h-8 bg-red-500 rounded mr-4"></div> {/* Placeholder for highlight */}
          <h3 className="text-2xl font-semibold" style={{ color: 'var(--button-color-2)' }}>Featured</h3>
        </div>

        {/* Main Featured Product */}
        <div className="mb-12 grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div className="relative bg-black rounded-sm overflow-hidden flex flex-col justify-center p-8 min-h-[300px]">
            <div className="relative z-10">
              <h3 className="text-3xl font-bold mb-2" style={{ color: 'var( --neutral-900)' }}>PlayStation 5</h3>
              <p className="text-gray-300 mb-6 max-w-sm">
                Experience lightning-fast loading with an ultra-high speed SSD, deeper immersion with support for haptic feedback, adaptive triggers, and 3D Audio, and an all-new generation of incredible PlayStation games.
              </p>
              <button className="px-6 py-3 rounded-md font-medium" style={{ backgroundColor: 'var(--color-secondary)', color: 'var(--neutral-900)' }}>
                Shop Now
              </button>
            </div>
            <div className="absolute inset-0">
              <img
                src="https://images.unsplash.com/photo-1606813907291-d86efa9b94db?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80" // Replace with actual PS5 image
                alt="PlayStation 5"
                className="object-cover object-center w-full h-full opacity-50"
              />
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {/* Smaller Featured Product 1 */}
            <div className="relative bg-red-800 rounded-sm overflow-hidden flex flex-col justify-end p-6 min-h-[200px]">
              <div className="relative z-10">
                <h3 className="text-xl font-bold mb-2" style={{ color: 'var( --neutral-900)' }}>Women's Collections</h3>
                <p className="text-gray-300 text-sm mb-4">Featured woman collections that are highly sought after.</p>
                <button className="text-sm font-medium underline" style={{ color: 'var(--color-warning)' }}>
                  Shop Now
                </button>
              </div>
              <div className="absolute inset-0">
                <img
                  src="https://images.unsplash.com/photo-1612432252325-fd0ee5eb8b11?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1974&q=80" // Replace with actual Women's Collection image
                  alt="Women's Collections"
                  className="object-cover object-center w-full h-full opacity-50"
                />
              </div>
            </div>

            {/* Smaller Featured Product 2 */}
            <div className="relative bg-gray-800 rounded-sm overflow-hidden flex flex-col justify-end p-6 min-h-[200px]">
              <div className="relative z-10">
                <h3 className="text-xl font-bold mb-2" style={{ color: 'var( --neutral-900)' }}>Speakers</h3>
                <p className="text-gray-300 text-sm mb-4">Browse our selection of high-quality speakers.</p>
                <button className="text-sm font-medium underline" style={{ color: 'var(--color-warning)' }}>
                  Shop Now
                </button>
              </div>
              <div className="absolute inset-0">
                <img
                  src="https://images.unsplash.com/photo-1545128363-c93569510693?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80" // Replace with actual Speaker image
                  alt="Speakers"
                  className="object-cover object-center w-full h-full opacity-50"
                />
              </div>
            </div>

            {/* Smaller Featured Product 3 (Perfume) - spans two columns on medium screens */}
            <div className="md:col-span-2 relative bg-blue-800 rounded-sm overflow-hidden flex flex-col justify-end p-6 min-h-[200px]">
              <div className="relative z-10">
                <h3 className="text-xl font-bold mb-2" style={{ color: 'var( --neutral-900)' }}>Perfume</h3>
                <p className="text-gray-300 text-sm mb-4">Discover captivating fragrances.</p>
                <button className="text-sm font-medium underline" style={{ color: 'var(--color-warning)' }}>
                  Shop Now
                </button>
              </div>
              <div className="absolute inset-0">
                <img
                  src="https://images.unsplash.com/photo-1603010230435-f4cc210a1d53?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2070&q=80" // Replace with actual Perfume image
                  alt="Perfume"
                  className="object-cover object-center w-full h-full opacity-50"
                />
              </div>
            </div>
          </div>
        </div>

        {/* Feature Icons */}
        <div className="flex flex-wrap justify-center gap-8">
          <div className="flex flex-col items-center text-center max-w-[150px]">
            <div className="w-16 h-16 rounded-full flex items-center justify-center mb-4" style={{ backgroundColor: 'var(--grayscale-off-white)' }}>
              {/* Placeholder Icon - Replace with actual SVG or Icon Component */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor" style={{ color: 'var( --neutral-900)' }}>
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8" />
              </svg>
            </div>
            <h4 className="text-lg font-semibold mb-2" style={{ color: 'var( --neutral-900)' }}>FREE AND FAST DELIVERY</h4>
            <p className="text-sm text-gray-400">Free delivery for all orders over $140</p>
          </div>

          <div className="flex flex-col items-center text-center max-w-[150px]">
            <div className="w-16 h-16 rounded-full flex items-center justify-center mb-4" style={{ backgroundColor: 'var(--grayscale-off-white)' }}>
              {/* Placeholder Icon - Replace with actual SVG or Icon Component */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor" style={{ color: 'var( --neutral-900)' }}>
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8m-9 13l-9-18h18l-9 18z" />
              </svg>
            </div>
            <h4 className="text-lg font-semibold mb-2" style={{ color: 'var( --neutral-900)' }}>24/7 CUSTOMER SERVICE</h4>
            <p className="text-sm text-gray-400">Friendly 24/7 customer support</p>
          </div>

          <div className="flex flex-col items-center text-center max-w-[150px]">
            <div className="w-16 h-16 rounded-full flex items-center justify-center mb-4" style={{ backgroundColor: 'var(--grayscale-off-white)' }}>
              {/* Placeholder Icon - Replace with actual SVG or Icon Component */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8" fill="none" viewBox="0 0 24 24" stroke="currentColor" style={{ color: 'var( --neutral-900)' }}>
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.615 3.595a2 2 0 01-.305 3.248 8.001 8.001 0 01-8.637-5.612c1.391-.664 2.488-1.95 2.881-3.46M12 18a9 9 0 100-18 9 9 0 000 18z" />
              </svg>
            </div>
            <h4 className="text-lg font-semibold mb-2" style={{ color: 'var( --neutral-900)' }}>MONEY BACK GUARANTEE</h4>
            <p className="text-sm text-gray-400">We return money within 30 days</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default FeaturedProduct;