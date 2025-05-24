/**
 * Renders the website footer with subscription, support, account, quick links, and app download sections.
 *
 * The footer includes a subscription form, contact information, navigation links, policy links, and promotional app download badges, all styled with responsive layout and custom color variables.
 *
 * @returns {JSX.Element} The footer section for the website.
 */
function Footer() {

    return (
        <div>

            {/* Footer (assuming similar to login/signup pages) */}
            <footer className="py-4 px-6" style={{ backgroundColor: 'var(--neutral-900)', color: 'var(--neutral-800)' }}>
                <div className="container mx-auto grid grid-cols-1 md:grid-cols-5 gap-8">
                    {/* Exclusive Column */}
                    <div>
                        <h3 className="text-xl font-bold mb-4 text-white">Exclusive</h3>
                        <p className="text-sm text-gray-400 mb-4">Subscribe</p>
                        <p className="text-sm text-gray-400 mb-2">Get 10% off your first order</p>
                        <div className="flex">
                            <input
                                type="email"
                                placeholder="Enter your email"
                                className="px-3 py-2 rounded-l-md text-sm flex-grow"
                                style={{ backgroundColor: 'var(--primary-color-1)', color: 'var(--neutral-100)', border: '1px solid var(--neutral-700)' }}
                            />
                            <button className="px-3 py-2 rounded-r-md flex items-center justify-center" style={{ backgroundColor: 'var(--error-600)', color: 'var(--grayscale-off-white)' }}>
                                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14 5l7 7m0 0l-7 7m7-7H3" />
                                </svg>
                            </button>
                        </div>
                    </div>

                    {/* Support Column */}
                    <div>
                        <h3 className="text-xl font-bold mb-4 text-white">Support</h3>
                        <ul className="space-y-2 text-sm text-gray-400">
                            <li><a href="#" className="hover:underline">111 Bijoy sarani, Dhaka, DH 1515, Bangladesh.</a></li>
                            <li><a href="#" className="hover:underline">exclusive@gmail.com</a></li>
                            <li><a href="#" className="hover:underline">+88015-88888-9999</a></li>
                        </ul>
                    </div>

                    {/* Account Column */}
                    <div>
                        <h3 className="text-xl font-bold mb-4 text-white">Account</h3>
                        <ul className="space-y-2 text-sm text-gray-400">
                            <li><a href="#" className="hover:underline">My Account</a></li>
                            <li><a href="#" className="hover:underline">Login / Register</a></li>
                            <li><a href="#" className="hover:underline">Cart</a></li>
                            <li><a href="#" className="hover:underline">Wishlist</a></li>
                            <li><a href="#" className="hover:underline">Shop</a></li>
                        </ul>
                    </div>

                    {/* Quick Link Column */}
                    <div>
                        <h3 className="text-xl font-bold mb-4 text-white">Quick Link</h3>
                        <ul className="space-y-2 text-sm text-gray-400">
                            <li><a href="#" className="hover:underline">Privacy Policy</a></li>
                            <li><a href="#" className="hover:underline">Terms Of Use</a></li>
                            <li><a href="#" className="hover:underline">FAQ</a></li>
                            <li><a href="#" className="hover:underline">Contact</a></li>
                        </ul>
                    </div>

                    {/* Download App Column */}
                    <div>
                        <h3 className="text-xl font-bold mb-4 text-white">Download App</h3>
                        <p className="text-sm text-gray-400 mb-4">Save $3 with App New User Discount</p>
                        <div className="flex space-x-2">
                            {/* Placeholder for app store badges */}
                            <img
                                src="https://via.placeholder.com/100x40?text=App+Store"
                                alt="App Store"
                                className=""
                            />
                            <img
                                src="https://via.placeholder.com/100x40?text=Google+Play"
                                alt="Google Play"
                                className=""
                            />
                        </div>
                    </div>
                </div>
                <div className="text-center text-gray-500 text-sm mt-8 border-t pt-6" style={{ borderColor: 'var(--primary-color-1)' }}>
                    &copy; Copyright Sayeed Ajmal 2025. All right reserved.
                </div>
            </footer>
        </div>
    );
}

export default Footer;
