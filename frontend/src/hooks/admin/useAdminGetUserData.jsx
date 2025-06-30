import { useEffect, useState } from "react";
import { getToken } from "../../services/TokenService";
import { adminGetUserData } from "../../services/ApiService";

export const useAdminGetUserData = (userId) => {
  const [userData, setUserData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
        const token = await getToken();
        if (!token) {
            throw new Error("No authentication token found");
        }
        const data = await adminGetUserData(token,userId);
        setUserData(data);
    } catch (err) {
        setError(err.message);
    } finally {
        setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return { userData, loading, error };
}