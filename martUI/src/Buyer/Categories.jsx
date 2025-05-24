import React from 'react';
import { MdOutlineArrowForwardIos, MdOutlineArrowBackIosNew, MdOutlineVisibility, MdOutlineFavoriteBorder } from 'react-icons/md'; // Assuming react-icons are installed
import ProductCard from './ProductCard';

function Categories() {
    const categories = [
        { name: 'Camera', icon: 'camera_alt' },
        { name: 'Computer', icon: 'computer' },
        { name: 'Gamepad', icon: 'sports_esports' },
        { name: 'Phone', icon: 'phone_iphone' },
        { name: 'Headphone', icon: 'headphones' },
        { name: 'Watch', icon: 'watch' },
    ];

    const products = [
        {
            id: 1,
            image: 'https://via.placeholder.com/270x192', // Replace with actual image URL
            discount: '40%',
            title: 'HAVIT HV-G92 Gamepad',
            price: '$120',
            originalPrice: '$160',
            rating: 4, // Assuming a rating out of 5
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
    const renderStars = (rating) => {
        const stars = [];
        for (let i = 0; i < 5; i++) {
            if (i < rating) {
                stars.push(<span key={i} className="material-symbols-outlined text-yellow-500 text-sm">star</span>);
            } else {
                stars.push(<span key={i} className="material-symbols-outlined text-gray-600 text-sm">star</span>);
            }
        }
        return stars;
    };

    return (
        <div className="text-white py-4 mt-3 text-black" style={{ backgroundColor: 'var(--primary-color-1)' }}>
            <div className="container mx-auto">
                {/* Categories Section */}
                <div className="mb-8">
                    <div className="flex items-center mb-4">
                        <div className="w-4 h-8 bg-red-500  rounded mr-4"></div> {/* Placeholder for highlight */}
                        <h3 className="text-red-500 font-semibold">Categories</h3>
                    </div>
                    <div className="flex justify-between items-center mb-6">
                        <h2 className="text-3xl font-bold">Browse By Category</h2>
                        <div className="flex space-x-4">
                            <button className="w-10 h-10  bg-neutral-700 flex items-center justify-center hover:bg-neutral-600 transition">
                                <MdOutlineArrowBackIosNew size={20} className="text-white" />
                            </button>
                            <button className="w-10 h-10  bg-neutral-700 flex items-center justify-center hover:bg-neutral-600 transition">
                                <MdOutlineArrowForwardIos size={20} className="text-white" />
                            </button>
                        </div>
                    </div>
                    <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-6">
                        {categories.map((category, index) => (
                            <div key={index} className="bg-neutral-50  p-6 flex flex-col items-center justify-center text-center space-y-3 hover:bg-red-400 transition cursor-pointer">
                                <span className="material-symbols-outlined --neutral-900 text-4xl">{category.icon}</span> {/* Assuming Material Symbols are linked */}
                                <p className="--neutral-900 text-sm font-medium">{category.name}</p>
                            </div>
                        ))}
                    </div>
                </div>

                {/* This Month Section */}
                <div className="mb-8">
                    <div className="flex items-center mb-4">
                        <div className="w-4 h-8 bg-red-500 rounded mr-4"></div> {/* Placeholder for highlight */}
                        <h3 className="text-red-500 font-semibold">This Month</h3>
                    </div>
                    <div className="flex justify-between items-center mb-6">
                        <h2 className="text-3xl font-bold">Best Selling Products</h2>
                        <button className="px-6 py-3 bg-red-500 text-white -md hover:bg-red-600 transition">
                            View All
                        </button>
                    </div>
                    <div className="overflow-x-auto">
                        <div className="flex overflow-x-auto space-x-6 pb-4 no-scrollbar">
                            {/* Product Card 1 */}
                            {products.map(product => (
                                <ProductCard key={product.id} product={product} />
                            ))}
                        </div>
                    </div>
                </div>

                {/* Feature Section (Placeholder) */}
                <div className="bg-red-500 h-96  flex items-center justify-center text-white text-3xl font-bold">
                    Feature Section Placeholder
                </div>
            </div>
        </div>
    );
}

export default Categories;
