
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import { setupStore } from './store/store.ts'
import { Slide, ToastContainer } from 'react-toastify'
const store = setupStore();
ReactDOM.createRoot(document.getElementById('root')!).render(

    <Provider store={store}>
        <ToastContainer transition={Slide} />
  <App />
 </Provider>
   

)