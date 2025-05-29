import WishProduct from "./WishProduct";
const Wishlist = () => {
  return (
    <div className="h-screen w-screen items-center justify-center">
      <div class="flex flex-col gap-[60px] justify-center">
        <div class="flex items-center gap-[835px]">
          <span class="font-normal text-[20px] leading-[26px] text-center text-black">
            Wishlist (4)
          </span>
          <div class="flex justify-center items-center gap-2.5 px-12 py-4 rounded border border-solid border-black">
            <span class="font-medium text-base text-black">
              Move All To Bag
            </span>
          </div>
        </div>
      </div>
        <WishProduct />
    </div>
  );
};

export default Wishlist;
