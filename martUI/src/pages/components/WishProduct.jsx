const WishProduct = ({ product }) => {
  return (
    <div className="flex flex-col w-full max-w-[270px] relative mx-auto">
      <div className="relative w-full h-[200px] sm:h-[250px] bg-gray-100 rounded overflow-hidden flex items-center justify-center">
        <img
          className="object-contain max-w-full max-h-full"
          src={product.imageUrl}
          alt={product.name}
        />
        <div className="absolute top-3 left-3 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded">
          -{product.discount}%
        </div>
      </div>
      <div className="absolute top-3 right-3 bg-white p-1.5 rounded-full shadow-md cursor-pointer">
        <svg
          className="w-5 h-5 text-gray-600"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
          ></path>
        </svg>
      </div>
      <div className="absolute bottom-0 left-0 right-0 bg-black text-white text-center cursor-pointer z-10">
        <button className="flex items-center justify-center w-full h-full p-2">
          <svg
            className="w-6 h-6 mr-2 text-white"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"
            ></path>
          </svg>
          Add To Cart
        </button>
      </div>
      <div className="flex flex-col">
        <span className="font-medium text-sm sm:text-base text-black">
          {product.name}
        </span>
        <span className="font-medium text-base text-red-500 mb-4">
          ${product.price}
        </span>
        {product.originalPrice && (
          <span className="font-medium text-base line-through text-black opacity-50">
            ${product.originalPrice}
          </span>)}
      </div>
    </div>
  );
};

export default WishProduct;

