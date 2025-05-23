import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import LoadingSwitch from "../../components/basic/LoadingSwitch/LoadingSwitch";
import useSearch from "../../hooks/useSearch";
import SearchResults from "../../components/search/SearchResults/SearchResults";
import NoResults from "../../components/basic/NoResults";

function Search() {
    let [searchParams] = useSearchParams();
    const { searchResults, loading, setSearchPage, setSearchText } = useSearch(searchParams.get('query'));

    useEffect(() => {
        setSearchText(searchParams.get('query'));
    }, [searchParams]);

    return (
        <LoadingSwitch loading={loading}>
            <div className="content-wrapper my-5">
                {
                    searchResults?.amountOfElements == 0 ? 
                    <NoResults title={searchParams.get('query')} /> 
                    : 
                    <SearchResults 
                        title={searchParams.get('query')} 
                        productsPage={searchResults} 
                        setPage={setSearchPage} 
                    />
                }
            </div>
        </LoadingSwitch>
    );
}

export default Search;