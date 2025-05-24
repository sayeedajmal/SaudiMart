/**
 * Renders a comprehensive, responsive seller dashboard UI for a B2B portal.
 *
 * The dashboard includes a sidebar navigation, mobile navbar, summary cards, recent orders table with pagination, inquiry tracker, recommended products, and supplier offers. All content is static and presentational, styled with Tailwind CSS and Material Symbols icons.
 *
 * @returns {JSX.Element} The seller dashboard layout as a React component.
 *
 * @remark
 * This component is purely presentational and does not include dynamic state management or event handling.
 */
function App() {
  return (
    <div>
      <div className="w-screen bg-gray-50 p-6 font-sans">
        <div className="flex flex-col lg:flex-row gap-6">
          {/* Sidebar - Hidden on mobile, visible on larger screens */}
          <div className="hidden lg:flex flex-col w-64 bg-white rounded-xl shadow-md p-5 h-[calc(100vh-3rem)]">
            <div className="flex items-center mb-8">
              <div className="w-10 h-10 rounded-full bg-primary-600 flex items-center justify-center text-white">
                <span className="material-symbols-outlined">business</span>
              </div>
              <div className="ml-3">
                <h2 className="font-bold text-lg">B2B Portal</h2>
                <p className="text-xs text-gray-500">Business Dashboard</p>
              </div>
            </div>

            <nav className="flex-1">
              <ul className="space-y-1">
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg bg-primary-50 text-primary-700 group transition-all duration-200 hover:bg-primary-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      dashboard
                    </span>
                    <span className="font-medium">Dashboard</span>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      shopping_cart
                    </span>
                    <span className="font-medium">My Orders</span>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      question_answer
                    </span>
                    <span className="font-medium">My Inquiries</span>
                    <span className="ml-auto bg-primary-100 text-primary-700 text-xs font-medium px-2 py-0.5 rounded-full">
                      8
                    </span>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      store
                    </span>
                    <span className="font-medium">Saved Suppliers</span>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      favorite
                    </span>
                    <span className="font-medium">Favorite Products</span>
                  </a>
                </li>
              </ul>
            </nav>

            <div className="pt-6 border-t border-gray-200">
              <ul className="space-y-1">
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">
                      settings
                    </span>
                    <span className="font-medium">Settings</span>
                  </a>
                </li>
                <li>
                  <a
                    href="#"
                    className="flex items-center p-3 rounded-lg text-gray-700 group transition-all duration-200 hover:bg-gray-100"
                  >
                    <span className="material-symbols-outlined mr-3">help</span>
                    <span className="font-medium">Support</span>
                  </a>
                </li>
              </ul>
            </div>
          </div>

          {/* Mobile navbar */}
          <div className="lg:hidden bg-white rounded-xl shadow-md p-4 mb-4">
            <div className="flex justify-between items-center">
              <div className="flex items-center">
                <div className="w-8 h-8 rounded-full bg-primary-600 flex items-center justify-center text-white">
                  <span className="material-symbols-outlined text-sm">
                    business
                  </span>
                </div>
                <h2 className="font-bold text-lg ml-2">B2B Portal</h2>
              </div>
              <details className="relative">
                <summary className="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center cursor-pointer hover:bg-gray-200 transition">
                  <span className="material-symbols-outlined">menu</span>
                </summary>
                <div className="absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 z-10">
                  <div className="py-1">
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Dashboard
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      My Orders
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      My Inquiries
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Saved Suppliers
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Favorite Products
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Settings
                    </a>
                    <a
                      href="#"
                      className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                    >
                      Support
                    </a>
                  </div>
                </div>
              </details>
            </div>
          </div>

          {/* Main content */}
          <div className="flex-1">
            {/* Header */}
            <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-6">
              <div>
                <h1 className="text-2xl font-bold text-gray-800">
                  Business Dashboard
                </h1>
                <p className="text-gray-500">
                  Welcome back, Tech Solutions Inc.
                </p>
              </div>

              <div className="mt-4 md:mt-0 flex items-center gap-4">
                <div className="relative flex-1 md:w-64">
                  <input
                    type="text"
                    placeholder="Search..."
                    className="w-full pl-10 pr-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-primary-500 transition"
                  />
                  <span className="material-symbols-outlined absolute left-3 top-2.5 text-gray-400">
                    search
                  </span>
                </div>

                <div className="relative">
                  <button className="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center hover:bg-gray-200 transition relative">
                    <span className="material-symbols-outlined text-gray-700">
                      notifications
                    </span>
                    <span className="absolute top-0 right-0 w-4 h-4 bg-red-500 text-white text-xs flex items-center justify-center rounded-full">
                      3
                    </span>
                  </button>
                </div>

                <div className="h-10 w-10 rounded-full bg-primary-600 text-white flex items-center justify-center">
                  <span className="font-medium">TS</span>
                </div>
              </div>
            </div>

            {/* Summary Cards */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
              <div className="bg-white p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 border-l-4 border-primary-500">
                <div className="flex items-start justify-between">
                  <div>
                    <p className="text-gray-500 mb-1 text-sm font-medium">
                      Active Orders
                    </p>
                    <h3 className="text-2xl font-bold">12</h3>
                    <p className="text-sm text-green-600 mt-2 flex items-center">
                      <span className="material-symbols-outlined text-sm mr-1">
                        trending_up
                      </span>
                      4 new this week
                    </p>
                  </div>
                  <div className="w-12 h-12 rounded-full bg-primary-100 flex items-center justify-center">
                    <span className="material-symbols-outlined text-primary-600">
                      local_shipping
                    </span>
                  </div>
                </div>
              </div>

              <div className="bg-white p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 border-l-4 border-amber-500">
                <div className="flex items-start justify-between">
                  <div>
                    <p className="text-gray-500 mb-1 text-sm font-medium">
                      Pending Responses
                    </p>
                    <h3 className="text-2xl font-bold">8</h3>
                    <p className="text-sm text-amber-600 mt-2 flex items-center">
                      <span className="material-symbols-outlined text-sm mr-1">
                        hourglass_top
                      </span>
                      3 require action
                    </p>
                  </div>
                  <div className="w-12 h-12 rounded-full bg-amber-100 flex items-center justify-center">
                    <span className="material-symbols-outlined text-amber-600">
                      message
                    </span>
                  </div>
                </div>
              </div>

              <div className="bg-white p-6 rounded-xl shadow-md hover:shadow-lg transition-shadow duration-300 border-l-4 border-blue-500">
                <div className="flex items-start justify-between">
                  <div>
                    <p className="text-gray-500 mb-1 text-sm font-medium">
                      Recently Viewed
                    </p>
                    <h3 className="text-2xl font-bold">24</h3>
                    <p className="text-sm text-blue-600 mt-2 flex items-center">
                      <span className="material-symbols-outlined text-sm mr-1">
                        history
                      </span>
                      7 products today
                    </p>
                  </div>
                  <div className="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center">
                    <span className="material-symbols-outlined text-blue-600">
                      visibility
                    </span>
                  </div>
                </div>
              </div>
            </div>

            {/* Order Table */}
            <div className="bg-white rounded-xl shadow-md p-6 mb-8">
              <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-6">
                <h2 className="text-xl font-bold">Recent Orders</h2>
                <div className="mt-3 sm:mt-0 flex items-center gap-2">
                  <div className="relative">
                    <select className="appearance-none pl-4 pr-10 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-primary-500 transition bg-white text-sm">
                      <option>All Orders</option>
                      <option>Pending</option>
                      <option>Shipped</option>
                      <option>Completed</option>
                    </select>
                    <span className="material-symbols-outlined absolute right-3 top-2 text-gray-500 pointer-events-none">
                      expand_more
                    </span>
                  </div>
                  <button className="px-4 py-2 rounded-lg bg-primary-600 text-white hover:bg-primary-700 transition flex items-center">
                    <span className="material-symbols-outlined mr-1 text-sm">
                      add
                    </span>
                    New Order
                  </button>
                </div>
              </div>

              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead>
                    <tr>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Order ID
                      </th>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Product
                      </th>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Supplier
                      </th>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Date
                      </th>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Amount
                      </th>
                      <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Status
                      </th>
                      <th className="px-4 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                        Actions
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    <tr className="hover:bg-gray-50 transition">
                      <td className="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #ORD-2857
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="h-10 w-10 rounded-md bg-gray-200 flex-shrink-0 mr-3 overflow-hidden">
                            <img
                              src="https://images.unsplash.com/photo-1546868871-0f936769675e"
                              alt="product"
                              className="h-full w-full object-cover"
                              keywords="electronics, semiconductor, chip"
                            />
                          </div>
                          <div className="text-sm text-gray-900">
                            Semiconductor Chips
                          </div>
                        </div>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        TechComponents Ltd.
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        Jun 21, 2023
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                        $4,250.00
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                          Shipped
                        </span>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button className="text-primary-600 hover:text-primary-900 mr-3">
                          View
                        </button>
                        <button className="text-gray-600 hover:text-gray-900">
                          Track
                        </button>
                      </td>
                    </tr>

                    <tr className="hover:bg-gray-50 transition">
                      <td className="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #ORD-2856
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="h-10 w-10 rounded-md bg-gray-200 flex-shrink-0 mr-3 overflow-hidden">
                            <img
                              src="https://images.unsplash.com/photo-1555664424-778a1e5e1b48"
                              alt="product"
                              className="h-full w-full object-cover"
                              keywords="industrial, machinery, equipment"
                            />
                          </div>
                          <div className="text-sm text-gray-900">
                            Industrial Equipment
                          </div>
                        </div>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        Global Machinery Inc.
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        Jun 18, 2023
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                        $12,750.00
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">
                          Pending
                        </span>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button className="text-primary-600 hover:text-primary-900 mr-3">
                          View
                        </button>
                        <button className="text-gray-600 hover:text-gray-900">
                          Track
                        </button>
                      </td>
                    </tr>

                    <tr className="hover:bg-gray-50 transition">
                      <td className="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #ORD-2855
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="h-10 w-10 rounded-md bg-gray-200 flex-shrink-0 mr-3 overflow-hidden">
                            <img
                              src="https://images.unsplash.com/photo-1586495777744-4413f21062fa"
                              alt="product"
                              className="h-full w-full object-cover"
                              keywords="chemical, material, laboratory"
                            />
                          </div>
                          <div className="text-sm text-gray-900">
                            Chemical Materials
                          </div>
                        </div>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        ChemSolutions Co.
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        Jun 15, 2023
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                        $3,125.00
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                          Processing
                        </span>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button className="text-primary-600 hover:text-primary-900 mr-3">
                          View
                        </button>
                        <button className="text-gray-600 hover:text-gray-900">
                          Track
                        </button>
                      </td>
                    </tr>

                    <tr className="hover:bg-gray-50 transition">
                      <td className="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        #ORD-2854
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="h-10 w-10 rounded-md bg-gray-200 flex-shrink-0 mr-3 overflow-hidden">
                            <img
                              src="https://images.unsplash.com/photo-1526406915894-7bcd65f60845"
                              alt="product"
                              className="h-full w-full object-cover"
                              keywords="office, supplies, stationery"
                            />
                          </div>
                          <div className="text-sm text-gray-900">
                            Office Supplies
                          </div>
                        </div>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        WorkSpace Essentials
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-500">
                        Jun 10, 2023
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-sm text-gray-900">
                        $785.00
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap">
                        <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                          Completed
                        </span>
                      </td>
                      <td className="px-4 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button className="text-primary-600 hover:text-primary-900 mr-3">
                          View
                        </button>
                        <button className="text-gray-600 hover:text-gray-900">
                          Track
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div className="flex justify-between items-center mt-6">
                <p className="text-sm text-gray-500">Showing 4 of 24 orders</p>
                <div className="flex">
                  <button className="px-3 py-1 rounded-l-lg border border-gray-300 bg-white text-gray-500 hover:bg-gray-50 disabled:opacity-50">
                    <span className="material-symbols-outlined text-sm">
                      chevron_left
                    </span>
                  </button>
                  <button className="px-3 py-1 border-t border-b border-gray-300 bg-primary-50 text-primary-700 font-medium">
                    1
                  </button>
                  <button className="px-3 py-1 border-t border-b border-gray-300 bg-white text-gray-500 hover:bg-gray-50">
                    2
                  </button>
                  <button className="px-3 py-1 border-t border-b border-gray-300 bg-white text-gray-500 hover:bg-gray-50">
                    3
                  </button>
                  <button className="px-3 py-1 rounded-r-lg border border-gray-300 bg-white text-gray-500 hover:bg-gray-50">
                    <span className="material-symbols-outlined text-sm">
                      chevron_right
                    </span>
                  </button>
                </div>
              </div>
            </div>

            {/* Inquiry Tracker and Recommended Products */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
              <div className="bg-white rounded-xl shadow-md p-6">
                <h2 className="text-xl font-bold mb-5">Inquiry Tracker</h2>
                <div className="space-y-4">
                  <div className="flex items-center p-3 rounded-lg border border-gray-200 hover:border-primary-300 hover:bg-primary-50 transition cursor-pointer">
                    <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center mr-4">
                      <span className="material-symbols-outlined text-blue-600">
                        question_answer
                      </span>
                    </div>
                    <div className="flex-1">
                      <h4 className="font-medium text-gray-900">
                        Industrial CNC Parts
                      </h4>
                      <p className="text-sm text-gray-500">
                        Response received from 3 suppliers
                      </p>
                    </div>
                    <span className="bg-green-100 text-green-800 text-xs px-2 py-1 rounded-full">
                      New Quotes
                    </span>
                  </div>

                  <div className="flex items-center p-3 rounded-lg border border-gray-200 hover:border-primary-300 hover:bg-primary-50 transition cursor-pointer">
                    <div className="w-10 h-10 rounded-full bg-amber-100 flex items-center justify-center mr-4">
                      <span className="material-symbols-outlined text-amber-600">
                        question_answer
                      </span>
                    </div>
                    <div className="flex-1">
                      <h4 className="font-medium text-gray-900">
                        Bulk Packaging Materials
                      </h4>
                      <p className="text-sm text-gray-500">
                        Waiting for 2 more suppliers
                      </p>
                    </div>
                    <span className="bg-amber-100 text-amber-800 text-xs px-2 py-1 rounded-full">
                      Pending
                    </span>
                  </div>

                  <div className="flex items-center p-3 rounded-lg border border-gray-200 hover:border-primary-300 hover:bg-primary-50 transition cursor-pointer">
                    <div className="w-10 h-10 rounded-full bg-purple-100 flex items-center justify-center mr-4">
                      <span className="material-symbols-outlined text-purple-600">
                        question_answer
                      </span>
                    </div>
                    <div className="flex-1">
                      <h4 className="font-medium text-gray-900">
                        Electronic Components
                      </h4>
                      <p className="text-sm text-gray-500">
                        Inquiry sent to 5 suppliers
                      </p>
                    </div>
                    <span className="bg-purple-100 text-purple-800 text-xs px-2 py-1 rounded-full">
                      In Progress
                    </span>
                  </div>
                </div>
                <button className="w-full mt-5 p-3 border border-dashed border-gray-300 rounded-lg text-gray-500 hover:border-primary-300 hover:text-primary-600 hover:bg-primary-50 transition flex items-center justify-center">
                  <span className="material-symbols-outlined mr-2">add</span>
                  Create New Inquiry
                </button>
              </div>

              <div className="bg-white rounded-xl shadow-md p-6">
                <h2 className="text-xl font-bold mb-5">Recommended Products</h2>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="border border-gray-200 rounded-lg p-3 hover:border-primary-300 hover:shadow-md transition cursor-pointer group">
                    <div className="h-32 w-full rounded-md bg-gray-100 mb-3 overflow-hidden">
                      <img
                        src="https://images.unsplash.com/photo-1561154464-82e9adf32764"
                        alt="product"
                        className="h-full w-full object-cover transform group-hover:scale-110 transition duration-300"
                        keywords="electronic, components, manufacturing"
                      />
                    </div>
                    <h4 className="font-medium text-gray-900">
                      LED Display Modules
                    </h4>
                    <div className="flex justify-between items-center mt-2">
                      <p className="text-sm text-gray-500">
                        Min. Order: 100 pcs
                      </p>
                      <span className="text-primary-600 text-sm font-medium">
                        $2.40 / pc
                      </span>
                    </div>
                  </div>

                  <div className="border border-gray-200 rounded-lg p-3 hover:border-primary-300 hover:shadow-md transition cursor-pointer group">
                    <div className="h-32 w-full rounded-md bg-gray-100 mb-3 overflow-hidden">
                      <img
                        src="https://images.unsplash.com/photo-1621451537084-482c73073a0f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MzkyNDZ8MHwxfHNlYXJjaHwxfHxwbGFzdGljfGVufDB8fHx8MTc0NzU0NTc3M3ww&ixlib=rb-4.1.0&q=80&w=1080"
                        alt="product"
                        className="h-full w-full object-cover transform group-hover:scale-110 transition duration-300"
                        keywords="plastic, injection, mold"
                      />
                    </div>
                    <h4 className="font-medium text-gray-900">
                      Custom Injection Molds
                    </h4>
                    <div className="flex justify-between items-center mt-2">
                      <p className="text-sm text-gray-500">
                        Min. Order: 10 sets
                      </p>
                      <span className="text-primary-600 text-sm font-medium">
                        $180.00 / set
                      </span>
                    </div>
                  </div>

                  <div className="border border-gray-200 rounded-lg p-3 hover:border-primary-300 hover:shadow-md transition cursor-pointer group">
                    <div className="h-32 w-full rounded-md bg-gray-100 mb-3 overflow-hidden">
                      <img
                        src="https://images.unsplash.com/photo-1584727638096-042c45049ebe"
                        alt="product"
                        className="h-full w-full object-cover transform group-hover:scale-110 transition duration-300"
                        keywords="metal, alloy, industrial"
                      />
                    </div>
                    <h4 className="font-medium text-gray-900">
                      Metal Alloy Sheets
                    </h4>
                    <div className="flex justify-between items-center mt-2">
                      <p className="text-sm text-gray-500">
                        Min. Order: 500 kg
                      </p>
                      <span className="text-primary-600 text-sm font-medium">
                        $3.25 / kg
                      </span>
                    </div>
                  </div>

                  <div className="border border-gray-200 rounded-lg p-3 hover:border-primary-300 hover:shadow-md transition cursor-pointer group">
                    <div className="h-32 w-full rounded-md bg-gray-100 mb-3 overflow-hidden">
                      <img
                        src="https://images.unsplash.com/photo-1534639077088-d702bcf685e7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MzkyNDZ8MHwxfHNlYXJjaHwxfHx0ZXh0aWxlfGVufDB8fHx8MTc0NzU0NTgwM3ww&ixlib=rb-4.1.0&q=80&w=1080"
                        alt="product"
                        className="h-full w-full object-cover transform group-hover:scale-110 transition duration-300"
                        keywords="textile, fabric, material"
                      />
                    </div>
                    <h4 className="font-medium text-gray-900">
                      Industrial Textiles
                    </h4>
                    <div className="flex justify-between items-center mt-2">
                      <p className="text-sm text-gray-500">
                        Min. Order: 1000 m
                      </p>
                      <span className="text-primary-600 text-sm font-medium">
                        $1.80 / m
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            {/* Supplier Offers */}
            <div className="bg-white rounded-xl shadow-md p-6 mb-6">
              <div className="flex justify-between items-center mb-6">
                <h2 className="text-xl font-bold">Offers from Suppliers</h2>
                <button className="text-primary-600 hover:text-primary-800 transition flex items-center">
                  View All
                  <span className="material-symbols-outlined ml-1">
                    arrow_forward
                  </span>
                </button>
              </div>

              <div className="flex flex-col md:flex-row gap-4 overflow-x-auto pb-2">
                <div className="min-w-[280px] border border-gray-200 rounded-xl p-4 hover:border-primary-300 hover:shadow-md transition cursor-pointer group">
                  <div className="flex justify-between items-start mb-3">
                    <div className="flex items-center">
                      <div className="w-10 h-10 rounded-full bg-gray-100 mr-3 overflow-hidden"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
