import WishProduct from "./WishProduct";

const Wishlist = () => {
  const products = [
    {
      id: 1,
      name: "Gucci duffle bag",
      price: 960,
      originalPrice: 1160,
      discount: 35,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 2,
      name: "Gaming Laptop",
      price: 880,
      originalPrice: null,
      discount: 35,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 3,
      name: "Apple Watch",
      price: 400,
      originalPrice: 500,
      discount: 20,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 4,
      name: "Nike Sneakers",
      price: 120,
      originalPrice: 150,
      discount: 20,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
  ];

  return (
    <div className="flex flex-col w-11/12 h-screen mx-auto px-4 overflow-scroll">
      <div className="flex items-center justify-between p-4">
        <span className="font-normal text-[20px] leading-[26px] text-center text-black">
          Wishlist ({products.length})
        </span>
        <div className="flex justify-center items-center px-6 py-2 rounded border border-solid border-black cursor-pointer">
          <span className="font-medium text-base text-black">
            Move All To Bag
          </span>
        </div>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4  justify-center">
        {products.map((product) => (
          <WishProduct key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default Wishlist;
