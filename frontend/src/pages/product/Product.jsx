import { useParams } from 'react-router-dom';
import ProductImages from "../../components/product/ProductImages/ProductImages";
import LoadingSwitch from '../../components/basic/LoadingSwitch/LoadingSwitch';
import ProductBuy from '../../components/product/ProductBuy/ProductBuy';
import InfoSectionV2 from '../../components/layout/InfoSection/InfoSectionV2';
import ProductDescription from '../../components/product/ProductDescription/ProductDescription';
import ProductCharacteristics from '../../components/product/ProductCharacteristics/ProductCharacteristics';
import { useEffect } from 'react';
import useProduct from '../../hooks/useProduct';
import { postComment, postReview } from '../../services/ApiService';
import { getToken } from '../../services/tokenService';
import ProductQuestionsAndResponses from '../../components/product/ProductQuestionsAndResponses/ProductQuestionsAndResponses';
import QuestionForm from '../../components/forms/QuestionForm/QuestionForm';
import ErrorPage from '../ErrorPage';
import ProductRating from '../../components/product/ProductReviews/ProductRating';
import ProductReviews from '../../components/product/ProductReviews/ProductReviews';


export default function Product() {
    const { idProduct } = useParams();
    const { product, loading, refresh } = useProduct(idProduct);

    const addComment = async (text) => {
        const token = await getToken();
        await postComment(token, idProduct, text);
        refresh();
    }

    useEffect(() => {
        window.scrollTo(0, 0)
    }, [])

    return (
        <LoadingSwitch loading={loading}>
            {
                product ?
                    <div className='content-wrapper d-flex flex-column my-3 bg-body rounded shadow-sm px-4 pt-3'>
                        <div className='d-flex w-100 gap-3' style={{ height: "400px" }}>
                            <ProductImages images={product?.pictures} />
                            <ProductBuy product={product} />
                        </div>
                        <InfoSectionV2 title="Características del producto">
                            <ProductCharacteristics characteristics={product?.attributes} />
                        </InfoSectionV2>

                        <InfoSectionV2 title="Descripción">
                            <ProductDescription description={product?.description} />
                        </InfoSectionV2>

                        <InfoSectionV2 title="Preguntas">
                            <QuestionForm
                                addQuestion={addComment}
                            />
                            <ProductQuestionsAndResponses
                                questions={product?.commentaries}
                            />
                        </InfoSectionV2>
                        <InfoSectionV2 title="Opiniones del producto">
                            {
                                product.reviews && product.reviews.length > 0 ?
                                    <div className='d-flex gap-3'>
                                        <ProductRating reviews={product.reviews} />
                                        <ProductReviews reviews={product.reviews} />
                                    </div>
                                    :
                                    <p className='text-muted text-center py-4'>No hay opiniones disponibles para este producto.</p>
                            }
                        </InfoSectionV2>
                    </div>
                    :
                    <ErrorPage />
            }
        </LoadingSwitch>
    );
}