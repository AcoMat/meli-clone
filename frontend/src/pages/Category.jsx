import { useParams } from 'react-router-dom';
import LoadingSwitch from "../components/basic/LoadingSwitch/LoadingSwitch";
import SearchResults from "../components/search/SearchResults/SearchResults";
import { useCategory } from '../hooks/useCategory';
import { useCategories } from '../hooks/useCategories';
import { useEffect } from 'react';
import Pagination from '../components/layout/Pagination/Pagination';

function Category() {
    const { id } = useParams();
    const { setCategoryID, setPage, productsPage, loading, error } = useCategory();
    const { categories } = useCategories();

    useEffect(() => {
        setCategoryID(id);
        setPage(1);
    }, [id]);

    useEffect(() => {
        console.log(productsPage);
        
    }, [productsPage]);

    return (
        <LoadingSwitch loading={loading}>
            <div className="content-wrapper my-4">
                {productsPage &&
                    <>
                        <SearchResults title={categories.find((cat) => cat.id == id).name} productsPage={productsPage} setPage={setPage} />
                    </>
                }
            </div>
        </LoadingSwitch>
    );
}

export default Category;