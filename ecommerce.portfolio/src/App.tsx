
import './App.css'

import { BrowserRouter, Route, Routes } from 'react-router'
import Layout from './components/Layout'
import Home from './pages/Home'
import Shop from './pages/Shop'
import Contact from './pages/Contact'
import About from './pages/About'
import Blog from './pages/Blog'
import Details from './pages/Details'
import Cart from './pages/Cart'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Checkout from './pages/Checkout'

function App() {


  return (
    <>
  
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Layout/>} >
      <Route index element={<Home/>} path='' />
      <Route path='/shop' element={<Shop/>}  />
      <Route path='/contact' element={<Contact/>}  />
      <Route path='/about' element={<About/>}  />
      <Route path='/blog' element={<Blog/>}  />
      <Route path='/details/:productId' element={<Details />} />
      <Route path='/cart' element={<Cart />} />
      <Route path='/login' element={<Login />} />
      <Route path='/signup' element={<Signup />} />
      <Route path='/checkout' element={<Checkout />} />
      </Route >
      
      </Routes>
      </BrowserRouter>

  
   
    </>
  )
}

export default App
