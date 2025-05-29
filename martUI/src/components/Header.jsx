import {
  MdFavoriteBorder,
  MdOutlineMan,
  MdOutlineShoppingBag,
  MdSearch,
} from "react-icons/md";

const ComponentName = () => {
  return (
    <header className="container mx-auto px-4 py-2 flex justify-between items-center">
      <h1
        className="text-2xl font-bold"
        style={{ color: "var(--neutral-900)" }}
      >
        Exclusive
      </h1>
      <nav className="hidden md:flex space-x-6">
        <a
          href="#"
          className="hover:underline"
          style={{ color: "var(--neutral-800)" }}
        >
          Home
        </a>
        <a
          href="#"
          className="hover:underline"
          style={{ color: "var(---neutral-800)" }}
        >
          Contact
        </a>
        <a
          href="#"
          className="hover:underline"
          style={{ color: "var(--neutral-800)" }}
        >
          About
        </a>
        <a
          href="#"
          className="hover:underline"
          style={{ color: "var(--neutral-800)" }}
        >
          Sign Up
        </a>
      </nav>
      <div className="flex items-center space-x-4">
        <div className="relative">
          <input
            type="text"
            placeholder="What are you looking for?"
            className="pl-8 pr-3 py-2  text-sm"
            style={{
              backgroundColor: "var(grayscale-label)",
              color: "var(--neutral-800)",
              border: "1px solid var(--neutral-700)",
            }}
          />
          <MdSearch
            className="absolute left-2 top-2.5"
            size={18}
            style={{ color: "var(--neutral-400)" }}
          />
        </div>
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8  bg-white flex items-center justify-center cursor-pointer">
            <MdFavoriteBorder
              size={20}
              style={{ color: "var(--neutral-700)" }}
            />
          </div>
          <div className="w-8 h-8  bg-white flex items-center justify-center cursor-pointer">
            <MdOutlineShoppingBag
              size={20}
              style={{ color: "var(--neutral-700)" }}
            />
          </div>
          <div className="w-8 h-8  bg-white flex items-center justify-center cursor-pointer">
            <MdOutlineMan size={20} style={{ color: "var(--neutral-700)" }} />
          </div>
        </div>
      </div>
    </header>
  );
};

export default ComponentName;
