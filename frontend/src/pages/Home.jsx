import AdminDashboard from "../components/admin/AdminDashboard";
import AdsCarousel from "../components/carousels/AdsCarousel/AdsCarousel";
import HomeCarousel from "../components/carousels/HomeCarousel/HomeCarousel";
import { useUserContext } from "../context/UserContext";

function Home() {
    const { user } = useUserContext();

    return (
        <>
            <AdsCarousel id={"1"} />
            <div className="d-flex flex-column content-wrapper gap-5">
                {
                    user && user.isAdmin ? (
                        <AdminDashboard />
                    ) : (
                        <HomeCarousel id={"1"} />
                    )
                }
            </div >
        </>
    );
}

export default Home;