import loadingSVG from '../../../assets/ui/loading.svg';

function LoadingSwitch({ children , loading, error}) {
    return (
        <>
            {loading ?
                <div className='d-flex justify-content-center align-items-center' style={{ height: "80vh" }}>
                    <img src={loadingSVG} />
                </div>
                :
                    children
                }
        </>
    );
}


export default LoadingSwitch;