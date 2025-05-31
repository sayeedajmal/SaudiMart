import React, { useState } from 'react';
import { FaStar, FaPlus, FaMinus, FaEye, FaHeart } from 'react-icons/fa';
const ProductDetails = ({ product }) => {
  const [mainImage, setMainImage] = useState(product?.images[0]);
  const [quantity, setQuantity] = useState(1);

  const handleThumbnailClick = (image) => {
    setMainImage(image);
  };

  const handleIncreaseQuantity = () => {
    setQuantity(quantity + 1);
  };

  const handleDecreaseQuantity = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  // Placeholder data for related items - replace with actual data fetching
  const relatedItems = [
    {
      id: 1,
      name: 'Related Item 1',
      price: '$120',
      discount: '-40%',
      image: 'https://via.placeholder.com/200x200', // Replace with actual image URL
      rating: 4,
    },
    {
      id: 2,
      name: 'Related Item 2',
      price: '$980',
      discount: '-35%',
      image: 'https://via.placeholder.com/200x200', // Replace with actual image URL
      rating: 5,
    },
    {
      id: 3,
      name: 'Related Item 3',
      price: '$370',
      discount: '-30%',
      image: 'https://via.placeholder.com/200x200', // Replace with actual image URL
      rating: 4,
    },
     {
      id: 4,
      name: 'Related Item 4',
      price: '$180',
      discount: '',
      image: 'https://via.placeholder.com/200x200', // Replace with actual image URL
      rating: 5,
    },
    // Add more related items as needed
  ];

  return (
    <div className="bg-black text-white min-h-screen p-8">
      <div className="flex flex-col md:flex-row gap-8 max-w-7xl mx-auto">
        <div className="flex flex-col gap-4">
          <div className="flex flex-col gap-2">
            {product?.images.map((image, index) => (
              <img
                key={index}
                src={image}
                alt={`Thumbnail ${index + 1}`}
                className={`w-24 h-auto cursor-pointer border-2 ${mainImage === image ? 'border-red-500' : 'border-transparent'}`}
                onClick={() => handleThumbnailClick(image)}
              />
            ))}
          </div>
          <div className="flex-grow">
            <img src={mainImage} alt={product?.name} className="w-full h-auto object-contain" />
          </div>
        </div>
        <div className="flex-1">
          <h1 className="text-3xl font-bold mb-2">{product?.name}</h1>
          <div className="flex items-center gap-1 mb-4">
            {[...Array(5)].map((_, i) => (
              <FaStar key={i} className={i < product?.rating ? 'text-yellow-500' : 'text-gray-600'} />
            ))}
          </div>
          <p className="text-green-500 font-semibold mb-4">In Stock</p> {/* Assuming always in stock for now */}
          <div className="flex flex-col gap-4 mb-6">
            <div>
              <label className="block text-gray-400 mb-1">Color:</label>
              <div className="flex gap-2">
                {/* Render color options - replace with actual data */}
                <div className="w-6 h-6 rounded-full bg-red-500 cursor-pointer"></div>
                <div className="w-6 h-6 rounded-full bg-blue-500 cursor-pointer"></div>
              </div>
            </div>
            <div>
              <label className="block text-gray-400 mb-1">Size:</label>
              <span className="inline-block border border-gray-600 px-3 py-1 rounded cursor-pointer">M</span>
            </div>
          </div>
          <div className="flex items-center gap-4 mb-6">
            <div className="flex items-center border border-gray-600 rounded">
              <button onClick={handleDecreaseQuantity} className="px-3 py-1 border-r border-gray-600"><FaMinus /></button>
              <span className="px-4 py-1">{quantity}</span>
              <button onClick={handleIncreaseQuantity} className="px-3 py-1 border-l border-gray-600"><FaPlus /></button>
            </div>
            <button className="bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-6 rounded">Buy Now</button>
          </div>
        </div>
      </div>

      <div className="mt-12 max-w-7xl mx-auto">
        <h2 className="text-2xl font-bold mb-6 border-l-4 border-red-600 pl-3">Related Item</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          {relatedItems.map((item) => (
            <div key={item.id} className="bg-[#1c1c1c] rounded-lg overflow-hidden shadow-lg relative group">
              {item.discount && (
                <div className="absolute top-2 left-2 bg-red-600 text-white text-xs font-bold px-2 py-1 rounded-full z-10">{item.discount}</div>
              )}
              <div className="absolute top-2 right-2 flex flex-col gap-2 opacity-0 group-hover:opacity-100 transition-opacity z-10">
                <FaEye className="icon" />
                <FaHeart className="icon" />
              </div>
              <img src={item.image} alt={item.name} />
              <div className="card-info">
                <h3>{item.name}</h3>
                 <div className="rating">
                 {[...Array(5)].map((_, i) => (
              <FaStar key={i} className={i < item.rating ? 'text-yellow-500' : 'text-gray-600'} />
            ))}
                </div>
                <p className="text-lg font-bold mt-2">{item.price}</p>
                <button className="mt-4 bg-white text-black font-semibold py-2 px-4 rounded w-full hover:bg-gray-200 transition-colors">Add To Cart</button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
