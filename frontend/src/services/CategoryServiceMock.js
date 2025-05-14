
import categories from "../mock/categories.json";
import productsJSON from "../mock/products.json";

export default function getCategories() {
    return new Promise((resolve, reject) => {
        return resolve(categories)
    })
}

export function getCategoriesById(id, pageNumber, pageSize = 5) {
    return new Promise((resolve, reject) => {
        const filteredProducts = productsJSON.products.filter(product => product.category.id === id)
        const res = {
            "products": filteredProducts.slice((pageNumber-1)*pageSize, pageNumber*pageSize),
            "currentPage": pageNumber,
            "amountOfPages": Math.max(1,Math.ceil(filteredProducts.length / pageSize)),
            "amountOfElements": filteredProducts.length
        }
        return resolve(res)
})};