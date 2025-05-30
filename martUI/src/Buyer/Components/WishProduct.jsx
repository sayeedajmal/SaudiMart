const WishProduct = ({ product }) => {
  return (
    <div className="flex flex-col gap-2 w-[270px] relative">
      <div className="relative w-full h-[250px] bg-gray-100 rounded overflow-hidden flex items-center justify-center">
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
      <div className="flex flex-col">
        <span className="font-medium text-base text-black">
          {product.name}
        </span>
        <div className="flex items-center gap-3">
          <span className="font-medium text-base text-red-500">
            ${product.price}
          </span>
          {product.originalPrice && (
            <span className="font-medium text-base line-through text-black opacity-50">
              ${product.originalPrice}
            </span>
          )}
        </div>
      </div>
    </div>
  );
};

export default WishProduct;

