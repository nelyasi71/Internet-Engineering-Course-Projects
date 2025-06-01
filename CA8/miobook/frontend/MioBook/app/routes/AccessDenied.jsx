import { Link } from 'react-router-dom';

const AccessDenied = ({ user, requiredRole }) => {
  return (
    <div className="container min-h-screen flex items-center justify-center bg-gradient-to-b from-gray-100 to-gray-200">
      <div className="bg-white p-10 rounded-xl shadow-2xl max-w-md w-full text-center transform transition-all hover:scale-105">
        <h1 className="text-4xl font-extrabold text-red-600 mb-6">Access Denied</h1>
        <p className="text-lg text-gray-700 mb-4">You do not have permission to access this page.</p>
        <div className="mb-6">
          <p className="text-gray-600">
            <span className="font-semibold">Required Role:</span> {requiredRole}
          </p>
          <p className="text-gray-600">
            <span className="font-semibold">Your Role:</span> {user.role}
          </p>
        </div>
        <Link
          to="/"
          className="inline-block px-6 py-3 bg-blue-600 font-semibold rounded-lg hover:bg-blue-700 transition-colors duration-300"
        >
          Return to Home
        </Link>
      </div>
    </div>
  );
};

export default AccessDenied;