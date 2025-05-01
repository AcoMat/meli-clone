import CategoryCard from "../components/cards/CategoryCard/CategoryCard";
import LoadingSwitch from "../components/basic/LoadingSwitch/LoadingSwitch";
import { useCategories } from "../hooks/useCategories";

const iconsRoot =  "/src/assets/category-icons/";

function Categories() {
    const { categories, loading } = useCategories();

    return (
        <LoadingSwitch loading={loading}>
            <div className="content-wrapper">
                <h2 className="m-4">Categorías</h2>
                <div className="row row-cols-4 text-center gap-3 my-4" >
                    {categories?.map((category) => (
                        <CategoryCard 
                        key={category.id} 
                        id={category.id} 
                        name={category.name.replace("-"," ")} 
                        icon={iconsRoot + category.id + ".svg"} 
                        />
                    ))}
                </div>
            </div>
        </LoadingSwitch>
    );
}

export default Categories;