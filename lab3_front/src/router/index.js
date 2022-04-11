import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Login from "@/views/Login";
import Admin from "@/views/Admin";
import Teacher from "@/views/Teacher";
import Student from "@/views/Student";
import PersonalInfo from "@/views/PersonalInfo";
import ViewCourse from "@/views/ViewCourse";
import CheckCourse from "@/views/CheckCourse";
import AddUser from "@/views/AddUser";
import CheckStudentInfo from "@/views/CheckStudentInfo";
import CheckTeacherInfo from "@/views/CheckTeacherInfo";
import CheckSchool from "@/views/CheckSchool";
import CheckMajor from "@/views/CheckMajor";


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
    path: "/:catchAll(.*)",
    redirect: '/login'
  }
  ]

export const admin_routes =
  {
    path: '/admin',
    name: 'Admin',
    component: Admin,
    children: [
        {
            path: 'checkstudentinfo',
            name: 'CheckStudentInfo',
            component: CheckStudentInfo
        },
        {
            path: 'checkteacherinfo',
            name: 'CheckTeacherInfo',
            component: CheckTeacherInfo
        },
        {
            path: 'checkschool',
            name: 'CheckSchool',
            component: CheckSchool
        },
        {
            path: 'checkmajor',
            name: 'CheckMajor',
            component: CheckMajor
        }
    ]
  }

export const teacher_routes =
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

export const student_routes =
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
  }



const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from) => {

    console.log(router.getRoutes())
    return true
})

export default router

