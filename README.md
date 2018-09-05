# Fission Workflow Sample: Carpool

Fission workflow sample mimics a carpool application. It tries to match a car owner who offers seats in his car and riders who are looking for one or more seats in a shared carpool. In current version for sake of simplicity there is no concept of of source & destination i.e. a single route is assumed.

Everything is a Fission function in this application and connecting the functions is done using the [Fission workflow project](https://github.com/fission/fission-workflows)

## Architecture

There are four custom functions and two Fission workflow's built in functions used to achieve an end to end carpool application. 

![Workflow](/static_assets/FissionWorkflow.png)

Please check the workflow definition in [Carpool workflow spec](./carpool-workflow.wf.yaml) while going over the description of each function below.

1. [CarPool producer](./01_carpool_producer)

Carpool producer produces a random car owner's with car plate and number of seats they can offer on ride.

2. [Foreach](https://github.com/fission/fission-workflows/blob/master/Docs/functions.md#foreach)

Foreach is a built in function in Fission workflow. Instead of writing a custom function for simple things like iterating over a list or checking a condition, you can use built in functions. In this case, we are getting a list of CarPool objects from CarPool producer and feeding one item at a time to CarPool validator.

3. [CarPool validator](./02_carpool_validator)

CarPool validator right now simply drops off the carpools which exceed a certain number of seats. 

4. [Compose](https://github.com/fission/fission-workflows/blob/master/Docs/functions.md#compose)

Although not clearly visible - a foreach is wrapped by a compose. Compose collects output of foreach and makes a list from individual outputs.

5. [CarPool allocator](./03_carpool_allocator)

Carpool allocator generates a set of rider requests and matches them with carpool requests. Currently the matching algorithm is rather rudimentry. This function also exposes a REST endpoint which reports matched rides, unmatched cars and unmatched riders.

6. [Carpool dashboard](./04_carpool_dashboard)

CarPool dashboard consumes the output of CarPool allocator and displays a simple dashboard

## Dashboards

![Dashboard 1/2](/static_assets/dashboard1.png)

![Dashboard 2/2](/static_assets/dashboard2.png)

## Try it out

If you have [Fission](https://github.com/fission/fission) and [Fission Workflows]((https://github.com/fission/fission-workflows)) installed, you can use specs to apply all functions and visit the dashboard URL at $FISSION_ROUTER/carpoolweb/index.html

```
$ fission spec apply
uploading archive archive://carpool-validator-1-0-0-jar-with-dependencies-jar
uploading archive archive://carpool-allocator-1-0-0-jar-with-dependencies-jar
uploading archive archive://carpool-producer-1-0-0-jar-with-dependencies-jar
5 functions created: carpool, cpallocator, cpproducer, cpvalidator, carpool-web
1 HTTPTrigger created: 408fd31e-5149-4c0c-acb0-9445d4a05f37
2 environments created: java, python
5 packages created: carpool-workflow-wf-yaml-hiri, carpool-allocator-1-0-0-jar-with-dependencies-jar-owhb, carpool-producer-1-0-0-jar-with-dependencies-jar-owhb, carpool-validator-1-0-0-jar-with-dependencies-jar-owhb, cp-web-pkg
```

## Future work

Although a working examplem, this can be enhanced further to make a real world carpool application

- Split the car riders generation into a different function.
- Make the matching algorithm more robust
- Use a document store to store requests from carpool creators and riders and then clean up based on a reasonable timeout like 30 minutes if no match found.