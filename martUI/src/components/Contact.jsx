import React from 'react';

const Contact = () => {
  return (
    <div className="container mx-auto py-8 px-4 md:px-0">
      {/* Breadcrumbs */}
      <div className="text-sm text-gray-600 mb-8">
        Home / Contact
      </div>

      <div className="flex h-[60vh] flex-col md:flex-row gap-8 items-start justify-center"> {/* Added items-start to align items to the top and justify-center to center */}
        {/* Contact Information Section */}
        <div className="md:w-1/3">
          <div className="bg-white p-6 rounded-md shadow-md">
            <div className="flex items-center mb-6">
              {/* Placeholder for Call icon */}
              <div className="bg-red-500 p-2 rounded-full mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
              </div>
              <h3 className="text-lg font-bold">Call To Us</h3>
            </div>
            <p className="text-gray-600 mb-4">We are available 24/7, 7 days a week.</p>
            <p className="text-gray-600">Phone: +8801611112222</p>

            <div className="border-t border-gray-200 my-6"></div>

            <div className="flex items-center mb-6">
              {/* Placeholder for Write icon */}
              <div className="bg-red-500 p-2 rounded-full mr-4">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8m-1 13H4a2 2 0 01-2-2V6a2 2 0 012-2h16a2 2 0 012 2v13a2 2 0 01-2 2z" />
                </svg>
              </div>
              <h3 className="text-lg font-bold">Write To Us</h3>
            </div>
            <p className="text-gray-600 mb-4">Fill out our form and we will contact you within 24 hours.</p>
            <p className="text-gray-600">Emails: customer@exclusive.com</p>
            <p className="text-gray-600">Emails: support@exclusive.com</p>
          </div>
        </div>

        {/* Contact Form Section */}
        <div className="md:w-2/3">
          <div className="bg-white p-6 rounded-md shadow-md">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
              <div>
                <input
                  type="text"
                  placeholder="Your Name *"
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                />
              </div>
              <div>
                <input
                  type="email"
                  placeholder="Your Email *"
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                />
              </div>
              <div className="md:col-span-2">
                 <input
                  type="text"
                  placeholder="Your Phone *"
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                />
              </div>
              <div className="md:col-span-2">
                <textarea
                  placeholder="Your Message"
                  rows="6"
                  className="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                ></textarea>
              </div>
            </div>
            <div className="flex justify-end">
              <button className="px-6 py-3 bg-red-500 text-white rounded-md hover:bg-red-600 transition duration-300">
                Send Message
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;