import useAdminGetTopFavorites from "../../../hooks/admin/useAdminGetTopFavorites";
import LoadingSwitch from "../../basic/LoadingSwitch/LoadingSwitch";
import TopProducts from "./TopProducts";

export default function TopFavorites() {
    const title = "Top Productos Favoritos";
    const { products, loading, error } = useAdminGetTopFavorites();

    return (
        <LoadingSwitch loading={loading} error={error}>
            <TopProducts title={title} topProducts={products} concept={"N° de Favoritos"}/>
        </LoadingSwitch>

    )
}