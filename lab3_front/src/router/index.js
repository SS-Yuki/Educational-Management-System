import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Login from "@/views/Login";
import Admin from "@/views/Admin";
import Teacher from "@/views/Teacher";
import Student from "@/views/Student";
import CheckCourse from "@/views/CheckCourse";
import CheckStudentInfo from "@/views/CheckStudentInfo";
import CheckTeacherInfo from "@/views/CheckTeacherInfo";
import CheckSchool from "@/views/CheckSchool";
import CheckMajor from "@/views/CheckMajor";
import CheckBuilding from "@/views/CheckBuilding";
import CheckClassroom from "@/views/CheckClassroom";
import CheckTime from "@/views/CheckTime";
import CheckApplication from "@/views/CheckApplication";
import TeacherInfo from "@/views/TeacherInfo";
import StudentInfo from "@/views/StudentInfo";
import TeacherCourse from "@/views/TeacherCourse";
import StudentCourse from "@/views/StudentCourse";


export const routes = [
  {
    path: '/',
    name: 'Home',
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
        },
        {
            path: 'checkbuilding',
            name: 'CheckBuilding',
            component: CheckBuilding
        },
        {
            path: 'checkclassroom',
            name: 'CheckClassroom',
            component: CheckClassroom
        },
        {
            path: 'checktime',
            name: 'CheckTime',
            component: CheckTime
        },
        {
            path: 'checkcourse',
            name: 'CheckCourse',
            component: CheckCourse
        },
        {
            path: 'checkapplication',
            name: 'CheckApplication',
            component: CheckApplication
        },
    ]
  }

export const teacher_routes =
  {
    path: '/teacher',
    name: 'Teacher',
    component: Teacher,
    children: [
      {
        path: 'teacherinfo',
        name: 'TeacherInfo',
        component: TeacherInfo
      },
      {
        path: 'teachercourse',
        name: 'TeacherCourse',
        component: TeacherCourse
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
        path: 'studentinfo',
        name: 'StudentInfo',
        component: StudentInfo
      },
      {
        path: 'studentcourse',
        name: 'StudentCourse',
        component: StudentCourse
      }
    ]
  }

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// router.beforeEach((to, from) => {
//     console.log("hhh")
//
//     //sessionStorage.setItem("user", JSON.stringify(res.data.data))
//     //console.log(store.state.routes_store)
//     if (!sessionStorage.getItem('routes')) {
//         console.log("kong le")
//         sessionStorage.setItem('routes', JSON.stringify(router.getRoutes()));
//         //store.commit('change_routes', router.getRoutes())
//     }
//
//     let store_routes = JSON.parse(sessionStorage.getItem('routes'))
//     let now_routes = router.getRoutes()
//     if (now_routes.length < store_routes.length) {
//         router.addRoute(admin_routes)
//         store_routes.forEach(item => {
//             console.log(typeof (item.components.default.__file))
//             console.log(item.path)
//             console.log(item.name)
//             console.log(item.default)
//
//             if (!now_routes.includes(item)) {
//                 router.addRoute({
//                         path: item.path,
//                         component: item.components.default.__file,
//                         name: item.name
//                     })
//                 // router.addRoute(item)
//             }
//         })
//     }
//     // if (router.getRoutes().find(item => item.name === to.name)) {
//     //     this.$store.routes_store.forEach(item => {
//     //         if (item.name === to.name) {}
//     //     })
//     //
//     // }
//     console.log(router.getRoutes())
//     console.log("haha")
//     console.log(JSON.parse(sessionStorage.getItem('routes')))
//     // if (to.matched.length === 0) { router.push(to.path)}
//     //router.push(to.path)
//     return true
// })

// router.afterEach((to, from) => {
//     if (from.path === "/login") {
//         console.log("liu")
//         console.log(store.state.routes_store.length)
//         if (store.state.routes_store.length < router.getRoutes().length) {
//             store.commit('change_routes', router.getRoutes())
//         }
//         console.log(store.state.routes_store)
//     }
// })

export default router

