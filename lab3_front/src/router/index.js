import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Login from "@/views/Login";
import Register from "@/views/Register";
import Admin from "@/views/Admin";
import Teacher from "@/views/Teacher";
import Student from "@/views/Student";
import PersonalInfo from "@/views/PersonalInfo";
import ViewCourse from "@/views/ViewCourse";
import CheckCourse from "@/views/CheckCourse";

// const routes = [
//   {
//     path: '/',
//     name: 'home',
//     component: HomeView
//   },
//   {
//     path: '/about',
//     name: 'about',
//     // route level code-splitting
//     // this generates a separate chunk (about.[hash].js) for this route
//     // which is lazy-loaded when the route is visited.
//     component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
//   }
// ]

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/admin',
    name: 'Admin',
    component: Admin
  },
  {
    path: '/student',
    name: 'Student',
    component: Student,
    children: [
      {
        path: 'personalinfo',
        name: 'PersonalInfo',
        component: PersonalInfo
      },
      {
        path: 'viewcourse',
        name: 'ViewCourse',
        component: ViewCourse
      }
    ]
  },
  {
    path: '/teacher',
    name: 'Teacher',
    component: Teacher,
    children: [
      {
        path: 'personalinfo',
        name: 'PersonalInfo',
        component: PersonalInfo
      },
      {
        path: 'checkcourse',
        name: 'CheckCourse',
        component: CheckCourse
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
