import { useEffect, useState } from "react";
import { getToken } from "../../services/TokenService";
import { adminGetTopBuyers } from "../../services/ApiService";

export default function useAdminGetTopBuyers() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
        const token = await getToken();
        if (!token) {
            throw new Error("No authentication token found");
        }
        const data = await adminGetTopBuyers(token);
        setUsers(data);
    } catch (err) {
        setError(err.message);
    } finally {
        setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return { users, loading, error };
}