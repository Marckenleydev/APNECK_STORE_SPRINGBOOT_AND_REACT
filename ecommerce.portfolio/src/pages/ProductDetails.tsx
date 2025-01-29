
import { useParams } from 'react-router';
import { productAPI } from '../services/ProductService';

import { Key } from '../enum/cache.key';
import { toastError } from '../services/ToastService';
import { PRODUCTS, PRODUCTS1 } from '../components/data';

const ProductDetails = () => {
  const { productId } = useParams();
  const { data: product } = productAPI.useFetchProductQuery(productId || '');
  const [addProductToCart] = productAPI.useAddProductToCartMutation();
  const [removeProductFromCart] = productAPI.useRemoveProductToCartMutation();
  const isLogin = localStorage.getItem(Key.LOGGEDIN) === "true";
  
const { data: cart, refetch, } =isLogin ?  productAPI.useFetchUserCartQuery(): { data: null };
  const handleAddProduct = async (productId: number) => {
    if(isLogin) {
    await addProductToCart(  productId );
  
  
    refetch
    } else {
      toastError('Please login to add products to cart')
    
    }
  };

 
  const handleRemoveProduct = async (productId: number) => {
    await removeProductFromCart(  productId );
    refetch
  };
  const quantity = cart?.products?.find((productCart) => productCart.productId === product?.id)?.quantity || 0;


  if (!product) {
    return null;
     // Show nothing if product is not available
  }

  // Retrieve quantity from cartItems for this product

  return (
    <div className="container p-5">
      <div className="row">
        <div className="col-lg-6">
          <div className="card p-5 m-auto">
            <img src={product.imageUrls[0]} alt={product.name} className="card-img-top img-fluid p-2" />
          </div>
        </div>

        <div className="col-lg-6">
          <div className="card p-3 m-auto">
            <div className="card-body">
              <h5 className="card-title">{product.brand}</h5>
              <h3 className="card-text">{product.name}</h3>
              <p className="card-text">
                <span className="text-danger fs-4 me-2">{product.price}$</span>
                <s>{product.price * 2}$</s>
              </p>
              <p className="card-text">{product.description}</p>

              {/* Quantity Control */}
              <div className="d-flex align-items-center mb-3 col-6">
                <button
                  className="btn btn-outline-secondary me-2"
                  onClick={()=>handleAddProduct(product.id)}
                >
                  +
                </button>
                <input
                  className="form-control text-center"
                  value={quantity}
                  
                />
                <button
                  className="btn btn-outline-secondary ms-2"
                  onClick={() => handleRemoveProduct(product.id)}
                >
                  -
                </button>
              </div>

              {/* Add to Cart */}
              <div className="d-flex justify-content-center">
                <button
                  onClick={() => handleAddProduct(product.id)}
                  className="btn btn-primary"
                >
                  Add To Cart {quantity > 0 && `(${quantity})`}
                </button>
              </div>
            </div>
          </div>
          <div className="card">
            <div className="d-flex justify-content-center flex-column align-items-center">
            <h2 className="text-center mb-2">More products of the same</h2>
            <p className="mb-2">We have more products, visit the shop to get amazing deals from us!!</p>
            </div>
            <div className="d-none d-md-block">
              <div className="row mb-3">
                <div className="col-6 col-md-4 col-lg-8 mx-auto">
                  <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3">
                    {PRODUCTS.slice(3, 7).map((product) => (
                      <div key={product.id} className="col">
                        <div className="card h-100">
                          <img src={product.image} className="card-img-top" alt="..." />
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              <div className="row mb-4">
                <div className="col-6 col-md-4 col-lg-8 mx-auto">
                  <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-3">
                    {PRODUCTS1.slice(2, 6).map((product) => (
                      <div key={product.id} className="col">
                        <div className="card h-100">
                          <img src={product.image} className="card-img-top" alt="..." />
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;
