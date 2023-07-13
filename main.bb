Graphics3D 640, 480, 16, 2

AppTitle "Hello Window", "Are you sure?"

SetBuffer BackBuffer()

; Player
head = CreatePivot()
camera = CreateCamera(head)
body = CreateCube(head)
Const head_type% = 1
Const body_type% = 2
Const camera_type% = 3
EntityType head, head_type%
EntityType body, body_type%
sprinting = False 


light = CreateLight()

ground = CreateTerrain(256)
Const ground_type% = 4
texture = LoadTexture("GFX/grass.png", 256)
EntityTexture ground, texture
EntityType ground, ground_type%


PositionEntity ground, -256/2, -2, -256/2

Const gravity# = -0.01

; Sound
footstep = LoadSound("SFX/Step.ogg")
footstepTimer# = 7.5
isWalking = False
isWalkingFNB = False ; is walking front and back ; fnb = front and back

; Gun
gun = LoadMesh("GFX/P90.b3d", head)
gunTexture = LoadTexture("GFX/p90texture.jpg")
EntityTexture gun, gunTexture
EntityOrder gun, -1
shake# = 5.0


; Sky
sky = CreateSphere(5)
skyTexture = LoadTexture("GFX/sky.png", 256)
EntityFX sky, 1+8
FlipMesh sky
ScaleEntity sky, 500, 500, 500
EntityTexture sky, skyTexture

HidePointer 

While Not KeyDown(1)
	RenderWorld
	UpdateWorld
	
	mxs# = mxs# + MouseXSpeed()
	mys# = mys# + MouseYSpeed()
	
	
	MoveMouse 640/2, 480/2
	
	
	
	If mxs# > 360 Then mxs# = 0
	If mxs# < 0 Then mxs# = 360
	
	If mys# > 80 Then mys# = 80
	If mys# < -80 Then mys# = -80	
	
	RotateEntity head, 0, -mxs#, 0
	RotateEntity camera, mys#, 0, 0
	RotateEntity gun, mys#, 0, 0
	
	
	If isWalking = True Or isWalkingFNB = True Then
		If sprinting = True Then 
			shake# = shake# - 0.15
		Else 
			shake# = shake# - 0.1
		EndIf 
		
		If shake >= 2.5 Then
			
			TranslateEntity gun, 0, -0.01, -0.01
			
		EndIf
		If shake <= 0.0 Then
			
			TranslateEntity gun, 0, 0.01, 0.01
			
		EndIf 
		If shake <= -2.5 Then 
			TranslateEntity gun, 0, 0, 0
			shake# = 5.0
		EndIf
		

		
	Else 
		TranslateEntity gun, 0, 0, 0
	EndIf
	
	If KeyDown(42) Then
		sprinting = True
	Else
		sprinting = False
	EndIf 
	
	
	If KeyDown(17) Then ; W
		If sprinting = True Then 
			MoveEntity head, 0, 0, 0.06
		
		Else 
			MoveEntity head, 0, 0, 0.03
			isWalkingFNB = True 
	 	EndIf
	
	ElseIf KeyDown(31) Then ; S
		If sprinting = True Then
			MoveEntity head, 0, 0, -0.06
		Else 
			MoveEntity head, 0, 0, -0.03
			isWalkingFNB = True
		EndIf 

	Else 
		isWalkingFNB = False 

	EndIf 


	If KeyDown(30) Then ; A
		If sprinting = True Then 
			MoveEntity head, -0.06, 0, 0
		Else 
			MoveEntity head, -0.03, 0, 0
			isWalking = True
		EndIf 
	ElseIf KeyDown(32) Then ; D
		
		If sprinting = True Then 
			MoveEntity head, 0.06, 0, 0
		Else 
			MoveEntity head, 0.03, 0, 0
			isWalking = True 
		EndIf 
	Else 
		isWalking = False
	EndIf
	 
	
	
	
	
		
	Collisions head_type%, ground_type%, 2, 2
	Collisions body_type%, ground_type%, 2, 2
	Collisions camera_type%, ground_type%, 2, 2
	

	If isWalking = True And isWalkingFNB = False Then
		If sprinting = True Then 
			footstepTimer# = footstepTimer# - 0.15
		Else 
			footstepTimer# = footstepTimer# - 0.1
		EndIf 

		If footstepTimer# <= 0.0 Then 
			PlaySound(footstep)
			footstepTimer# = 7.5
		EndIf 

	ElseIf isWalkingFNB = True And isWalking = False Then
		If sprinting = True Then 
			footstepTimer# = footstepTimer# - 0.15
		Else 
			footstepTimer# = footstepTimer# - 0.1
		EndIf 
			If footstepTimer# <= 0.0 Then 
			PlaySound(footstep)
			footstepTimer# = 7.5
		EndIf 

	ElseIf isWalkingFNB = True And isWalking = True Then
		If sprinting = True Then 
			footstepTimer# = footstepTimer# - 0.15
		Else 
			footstepTimer# = footstepTimer# - 0.1
		EndIf 
		If footstepTimer# <= 0.0 Then 
			PlaySound(footstep)
			footstepTimer# = 7.5
		EndIf 	
		
	
	EndIf
	
	
	Flip
	Cls
Wend


End