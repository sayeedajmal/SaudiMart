import {
    MdAccountCircle,
    MdDashboard,
    MdNotifications,
    MdSearch,
} from "react-icons/md";

function Dashboard() {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      <header className="bg-primary text-white p-4 flex justify-between items-center">
        <h1 className="text-2xl font-title">IndiaMart Dashboard</h1>
        <div className="flex items-center space-x-4">
          <MdSearch size={24} />
          <MdNotifications size={24} />
          <MdAccountCircle size={24} />
        </div>
      </header>
      <main className="flex-grow p-4">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div className="bg-white p-6 rounded-lg shadow-md">
            <MdDashboard size={48} className="text-primary mb-4" />
            <h2 className="text-xl font-semibold mb-2">Overview</h2>
            <p>View your account overview and statistics here.</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md">
            <MdDashboard size={48} className="text-primary mb-4" />
            <h2 className="text-xl font-semibold mb-2">Orders</h2>
            <p>Check your recent and past orders.</p>
          </div>
          <div className="bg-white p-6 rounded-lg shadow-md">
            <MdDashboard size={48} className="text-primary mb-4" />
            <h2 className="text-xl font-semibold mb-2">Messages</h2>
            <p>Communicate with your buyers and sellers.</p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;
