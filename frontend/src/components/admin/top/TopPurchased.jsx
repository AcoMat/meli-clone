import useAdminGetTopPurchased from "../../../hooks/admin/useAdminGetTopPurchased";
import LoadingSwitch from "../../basic/LoadingSwitch/LoadingSwitch";
import TopProducts from "./TopProducts";

export default function TopPurchased() {
    const title = "Productos más comprados";
    const { products, loading, error } = useAdminGetTopPurchased();

    return (
        <LoadingSwitch loading={loading} error={error}>
            <TopProducts title={title} topProducts={products} concept={"N° de compras"} />
        </LoadingSwitch>

    )
}