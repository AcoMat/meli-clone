import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import LoadingSwitch from "../../components/basic/LoadingSwitch/LoadingSwitch";
import useSearch from "../../hooks/useSearch";
import SearchResults from "../../components/search/SearchResults/SearchResults";
import NoResults from "../../components/basic/NoResults";
import Pagination from "../../components/layout/Pagination/Pagination";

function Search() {
    let [searchParams] = useSearchParams();
    const { searchResults, loading, searchPage, setSearchPage, setSearchText } = useSearch(searchParams.get('query'));

    useEffect(() => {
        setSearchPage(1);
        setSearchText(searchParams.get('query'));
    }, [searchParams]);

    return (
        <div className="content-wrapper my-5">
            {
                searchResults?.amountOfElements == 0 ?
                    <NoResults title={searchParams.get('query')} />
                    :
                    <>
                        <SearchResults
                            title={searchParams.get('query')}
                            productsPage={searchResults}
                            loading={loading}
                        />
                        <Pagination
                            page={searchPage}
                            setPage={setSearchPage}
                        />
                    </>
            }
        </div>
    );
}

export default Search;