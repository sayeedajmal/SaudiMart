import React from 'react';
import { MdOutlineFavoriteBorder, MdOutlineRemoveRedEye } from 'react-icons/md';
import { FaStar, FaStarHalfAlt, FaRegStar } from 'react-icons/fa';

function ProductCard({ product }) {
  const renderRatingStars = (rating) => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      if (i <= rating) {
        stars.push(<FaStar key={i} className="text-yellow-500" />);
      } else if (i === Math.ceil(rating) && rating % 1 !== 0) {
        stars.push(<FaStarHalfAlt key={i} className="text-yellow-500" />);
      } else {
        stars.push(<FaRegStar key={i} className="text-gray-400" />);
      }
    }
    return stars;
  };

  return (
    <div className="relative bg-white  overflow-hidden shadow-md group hover:shadow-lg transition-shadow duration-300" style={{ backgroundColor: 'var(--grayscale-off-white)' }}>
      <div className="relative w-full h-48 bg-gray-200 flex items-center justify-center overflow-hidden">
        <img
          src={product.image}
          alt={product.title}
          className="w-full h-full object-cover transform group-hover:scale-110 transition duration-300"
        />
        {product.discount && (
          <div className="absolute top-2 left-2 bg-error-500 text-white text-xs font-bold px-2 py-1 rounded" style={{ backgroundColor: 'var(--color-error)' }}>
            -{product.discount}%
          </div>
        )}
        <div className="absolute top-2 right-2 flex flex-col space-y-2 opacity-0 group-hover:opacity-100 transition-opacity">
          <button className="p-1 bg-white shadow hover:bg-gray-100">
            <MdOutlineFavoriteBorder size={20} />
          </button>
          <button className="p-1 bg-white shadow hover:bg-gray-100">
            <MdOutlineRemoveRedEye size={20} />
          </button>
        </div>
        <button className="absolute bottom-0 left-0 w-full bg-black text-white text-sm py-2 opacity-0 group-hover:opacity-100 transition-opacity" style={{ backgroundColor: 'var(--neutral-900)' }}>
          Add To Cart
        </button>
      </div>
      <div className="p-4">
        <h3 className="text-lg font-medium mb-1" style={{ color: 'var(--grayscale-title-active)' }}>{product.title}</h3>
        <div className="flex items-center mb-2">
          <span className="text-primary-600 font-semibold mr-2" style={{ color: 'var(--color-primary)' }}>${product.price}</span>
          {product.originalPrice && (
            <span className="text-gray-500 text-sm line-through">${product.originalPrice}</span>
          )}
        </div>
        <div className="flex items-center">
          {renderRatingStars(product.rating)}
          <span className="text-gray-500 text-sm ml-2">({product.reviews})</span>
        </div>
      </div>
    </div>
  );
}

export default ProductCard;