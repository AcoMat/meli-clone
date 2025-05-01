import productsJSON from "../mock/products.json";

export function getProduct(id) {
    return new Promise((resolve, reject) => {
        const res = productsJSON.products.find(product => product.id === id)
        if (res) {
            resolve(res)
        } else {
            reject("Product not found")
        }
    })
};

/**
 * @param {number} pageNumber
 * @returns {Promise<pageDTO>}
 */
export default function getPageWithProducts(pageNumber, pageSize = 5) {
    return new Promise((resolve, reject) => {
        return resolve({
            "products": productsJSON.products.slice((pageNumber-1)*pageSize, pageNumber*pageSize),
            "currentPage": pageNumber,
            "amountOfPages": Math.ceil(productsJSON.products.length / pageSize),
            "amountOfElements": productsJSON.products.length
        })
})};


