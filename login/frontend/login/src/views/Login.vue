<template>
  <div id="loginform">
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        id="input-group-1"
        label="Username:"
        label-for="input-1"
        description="Please input your username"
      >
        <b-form-input
          id="input-1"
          v-model="form.username"
          type="text"
          placeholder="Enter username"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Password:" label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="form.password"
          type="password"
          placeholder="Enter password"
          required
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
      <b-button type="reset" variant="danger">Reset</b-button>
    </b-form>
    <b-card class="mt-3" header="Form Data Result">
      <pre class="m-0">{{ form }}</pre>
    </b-card>
  </div>
</template>

<script>
import { BForm, BFormInput, BFormGroup, BCard, BButton } from "bootstrap-vue";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// Vue.component("b-form", BForm);
const inststance = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  components: {
    "b-form": BForm,
    "b-form-input": BFormInput,
    "b-form-group": BFormGroup,
    "b-card": BCard,
    "b-button": BButton,
  },
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      foods: [
        { text: "Select One", value: null },
        "Carrots",
        "Beans",
        "Tomatoes",
        "Corn",
      ],
      show: true,
    };
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      const params = new URLSearchParams();
      params.append("username", this.form.username);
      params.append("password", this.form.password);

      inststance
        .post("/api/login", params, { withCredentials: true })
        .then((res) => {
          alert(JSON.stringify(res));
          inststance.get("/api/", { withCredentials: true }).then((res) => {
            alert(JSON.stringify(res.data));
          });
        })
        .catch((err) => {
          alert("请求服务器失败,请稍后重试" + JSON.stringify(err));
        });
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.username = "";
      this.form.password = "******";
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>

<style  scoped>
#loginform {
  margin: 0 auto;
  padding: 20px;
  text-align: start;
  max-width: 500px;
  background-color: rgb(145, 153, 150);
}
</style>

