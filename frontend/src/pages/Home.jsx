import LoadingSwitch from "../components/basic/LoadingSwitch/LoadingSwitch";
import AdsCarousel from "../components/carousels/AdsCarousel/AdsCarousel";
import HomeCarousel from "../components/carousels/Home/HomeCarousel";
import ProductsCarousel from "../components/carousels/Products/ProductsCarousel";
import useProducts from "../hooks/useProducts";

function Home() {
    const { productsPage, loading, error } = useProducts();

    return (
        <LoadingSwitch loading={loading}>
            {/* <AdsCarousel /> */}
            <div className="d-flex flex-column content-wrapper gap-5 my-5">
                <HomeCarousel id={"1"} products={productsPage?.products?.slice(0,12)} large={false} />
                <ProductsCarousel id={"2"} products={productsPage?.products?.slice(0,12)} title={"Ofertas"} large={false} />
                <ProductsCarousel id={"3"} products={productsPage?.products?.slice(0,12)} title={"Podría interesarte"} large={true} />
            </div>
        </LoadingSwitch>
    );
}

export default Home;