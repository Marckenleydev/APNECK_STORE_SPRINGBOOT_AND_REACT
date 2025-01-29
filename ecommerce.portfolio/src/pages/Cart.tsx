
import  {  useState } from 'react'

import CartItem from '../components/CartItem'
import { useNavigate } from 'react-router-dom'
import { productAPI } from '../services/ProductService'
import { stripeAPI } from '../services/StripeService'
import { Key } from '../enum/cache.key'


const Cart = () => {
  const isLogin = localStorage.getItem(Key.LOGGEDIN) === "true";
    const { data: cart, refetch } = isLogin ? productAPI.useFetchUserCartQuery() : {data: null, refetch: null};
    const [proceedCheckout,{ isLoading }] = stripeAPI.useProceedCheckoutMutation();
    const [clearCart] = productAPI.useClearToCartMutation();

    const handleClearProduct = async () => {

      await clearCart();
      
      refetch;
    };

    const handleProceedToCheckout = async (proceedCheckout:any) => {
      try {
        const response = await proceedCheckout().unwrap();
        if (response?.sessionURL) {
          // Redirect to the Stripe Checkout URL
          window.location.href = response.sessionURL;
        } else {
          console.error("Session URL not found in response");
        }
      } catch (error) {
        console.error("Error during checkout:", error);
      }
    };
    
    console.log(cart)
   
    const navigate = useNavigate();
    const [isMobile, setIsMobile] = useState(false)

    const handleResize = () => {
      if (window.innerWidth < 576) {
        setIsMobile(true)
      } else {
        setIsMobile(false)
      }
    }
  
    window.addEventListener("resize", handleResize)
  if(!cart){
    return (
      <div className="container-xxl">
        <div className="row">
          <div className="text-center p-5 mb-4">
            <h2>Your Cart Is Empty!!!</h2>
          </div>
        </div>
      </div>
    )
  }
  return <>
<section className="cart">
  <div className="container-xxl p-5">
    
      <div className="row">
        <div className="p-2 text-center">
          <h2>Cart</h2>
        </div>
        <div className="col-12 col-md-5 text-center">
          <h5>Product</h5>
        </div>
        <div className="col-12 col-md-5 text-center">
          <h5>Details</h5>
        </div>

        <div className="p-3">
          {cart?.products.map((product) => {
           
              return <CartItem key={product.productId} data={product} refetchCart={refetch} />;
            
          })}
          <div  onClick={handleClearProduct} className="col-12 p-2 text-end">
            <button  id="clear-cart">Clear Cart</button>
          </div>

          <hr />
          <div className="row">
            <div className="col-12 col-md-6 d-flex m-auto justify-content-center mt-4">
              <button onClick={() => navigate("/shop")}>
                {isMobile ? "Continue" : "Continue Shopping"}
              </button>
            </div>

            <div className="col-12 col-md-6 total m-auto d-flex flex-column p-5">
              <div className="col-12">
                <div className="text-end">
                  <h2 className="mb-3">
                    <b>Total</b>
                  </h2>
                  <div className="align-items-center">
                    <div>
                      <p className="total-price align-items-center">
                        <b>${cart?.amount}</b>
                      </p>
                    </div>
                  </div>
                  <button
                    onClick={() => handleProceedToCheckout(proceedCheckout)}
                    className="mt-5"
                  >
                    {isLoading ? "Processing..." : "Proceed to Checkout"}
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
   
    
  </div>
</section>

  </>;
}

export default Cart