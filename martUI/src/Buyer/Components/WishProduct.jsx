import React from "react";

const WishProduct = () => {
  return (
    <div class="flex flex-col gap-4">
      <div class="w-[270px] h-[250px] bg-neutral-100 rounded">
        <div class="w-[270px] h-[41px] bg-black"></div>
        <div class="flex flex-col gap-2">
          <div class="w-[34px] h-[34px]">
            <div class="w-6 h-6">
              <svg class="w-4 h-[18px]"></svg>
            </div>
          </div>
        </div>
        <div class="flex justify-center items-center gap-2.5 bg-[#db4444] px-3 py-1 rounded">
          <span class="font-normal text-[12px] leading-[18px] text-neutral-50">
            -35%
          </span>
        </div>
        <div class="w-[190px] h-[180px]">
          <img class="w-[178px] h-[129px]"></img>
        </div>
        <div class="flex items-center gap-2">
          <div class="w-6 h-6">
            <svg class="w-[1.5px] h-[1.5px]"></svg>
            <svg class="w-[1.5px] h-[1.5px]"></svg>
            <svg class="w-[17.25px] h-[12.75px]"></svg>
            <svg class="w-[14.91776180267334px] h-[7.5px]"></svg>
          </div>
          <span class="font-normal text-[12px] leading-[18px] text-white">
            Add To Cart
          </span>
        </div>
      </div>
      <div class="flex flex-col gap-2">
        <span class="font-medium text-base text-black">Gucci duffle bag</span>
        <div class="flex gap-3">
          <span class="font-medium text-base text-[#db4444]">$960</span>
          <span class="font-medium text-base strikethrough text-black opacity-50">
            $1160
          </span>
        </div>
      </div>
    </div>
  );
};

export default WishProduct;
