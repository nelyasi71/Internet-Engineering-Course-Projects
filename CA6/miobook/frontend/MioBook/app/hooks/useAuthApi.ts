import axiosInstance from "../api/axiosInstance";

const useAuthApi = () => {
    const login = async (username: string, password: string) => {
        const response = await axiosInstance.post("/auth/login", {
            username,
            password,
        });

        if (response.data.success) {
            localStorage.setItem("accessToken", response.data.data.token);
        }

        return response.data;
    };

    const logout = async () => {
        await axiosInstance.post("/auth/logout");
        localStorage.removeItem("accessToken");
    };

    const getUser = async () => {
        const response = await axiosInstance.get("/auth/user");
        return response.data.data;
    };

    const getUserRole = async (username: string) => {
        const response = await axiosInstance.get(`/users/${username}`);
        return response.data.data.role;
    };

    return { login, logout, getUser, getUserRole };
};

export default useAuthApi;
