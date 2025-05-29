import { useEffect, useState } from "react";
import { searchProducts } from "../services/ApiService";

export default function useSearch(query) {
  const [searchResults, setSearchResults] = useState(null);
  const [searchPage, setSearchPage] = useState(1);
  const [searchText, setSearchText] = useState(query);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    searchProducts(query, searchPage)
      .then((response) => {
        setSearchResults(response);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching search results:", error);
        setLoading(false);
      });
  }, [query, searchPage, searchText]);


  return { searchResults, loading, setSearchPage, setSearchText };
};