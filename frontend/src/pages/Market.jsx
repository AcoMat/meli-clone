import notFound from "../assets/ui/404.svg";
export default function Market() {
    return (
        <>
            <div className="d-flex flex-column justify-content-center align-items-center my-5" style={{paddingTop:"20vh"}}>
                    <img src={notFound} alt="404 Not Found" />
                    <h4 className="m-4">Esta pagina todavia está en desarrollo</h4>
                    <a href="/">Ir a página principal</a>
            </div>
        </>
    );
}