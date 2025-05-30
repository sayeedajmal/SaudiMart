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
    {
      id: 5,
      name: "Leather Wallet",
      price: 70,
      originalPrice: 100,
      discount: 30,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 6,
      name: "Sony PlayStation 5",
      price: 500,
      originalPrice: null,
      discount: 35,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 7,
      name: "Dyson Supersonic Hair Dryer",
      price: 400,
      originalPrice: 500,
      discount: 20,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 8,
      name: "Kindle Paperwhite",
      price: 130,
      originalPrice: null,
      discount: 10,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },
    {
      id: 9,
      name: "Logitech MX Master 3S",
      price: 100,
      originalPrice: 120,
      discount: 17,
      imageUrl:
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdWBFFGiM3vPRmndjkK3OqLKJJGW0nX2552g&s",
    },

  ];

  return (
    <div className="flex flex-col gap-[60px]">
      <div className="flex flex-col md:flex-row items-center justify-between gap-4">
        <span className="font-normal text-[20px] leading-[26px] text-center text-black">
          Wishlist ({products.length})
        </span>
        <div className="flex justify-center items-center p-2 m-2 rounded border border-solid border-black cursor-pointer">
          <span className="font-medium text-base text-black whitespace-nowrap">
            Move All To Bag
          </span>
        </div>
      </div>
      <div className="flex flex-wrap justify-center gap-[30px] pb-4">
        {products.map((product) => (
          <WishProduct key={product.id} product={product} />
        ))}
      </div>
    </div>
  );
};

export default Wishlist;
